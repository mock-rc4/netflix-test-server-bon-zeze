package com.example.demo.src.lineAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.lineAccount.domain.LineAccount;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/line-accounts")
public class LineAccountController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final LineAccountService lineAccountService;
	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final LineAccountProvider lineAccountProvider;

	@Value("${url.base}")
	private String baseUrl;

	@Value("${social.line.client-id}")
	private String lineClientId;

	@Value("${social.line.client-secret}")
	private String lineClientSecret;

	@Value("${social.line.redirect}")
	private String lineRedirectUri;

	private final Environment env;

	public LineAccountController(LineAccountService lineAccountService, JwtService jwtService,
		LineAccountProvider lineAccountProvider, Environment env) {
		this.lineAccountService = lineAccountService;
		this.jwtService = jwtService;
		this.lineAccountProvider = lineAccountProvider;
		this.env = env;
	}

	// 네이버 로그인창 URL 을 가져오는 API
	@RequestMapping(value = "auth-url")
	public @ResponseBody
	BaseResponse<String> getLineAuthUrl() throws Exception {
		String reqUrl = env.getProperty("social.line.url.login")
			+ "?response_type=code"
			+ "&client_id=" + lineClientId
			+ "&redirect_uri="
			+ baseUrl + lineRedirectUri
			+ "&state=" + makeRandomNumber()
			+ "&scope=profile%20openid%20email"; // 난수 생성
		return new BaseResponse<>(reqUrl);
	}

	private String makeRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	// lineAccount에 접근 가능한 line Access Token을 반환
	@ResponseBody
	@GetMapping("/redirect")
	public BaseResponse<String> redirectLine(@RequestParam() String code,

		@RequestParam() String state) throws BaseException {

		try {
			LineAccount.GetLineTokenRes getLineTokenResDto = getLineTokenInfo(code);
			return new BaseResponse<>(getLineTokenResDto.getId_token());
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	public LineAccount.GetLineTokenRes getLineTokenInfo(String code) throws BaseException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", lineClientId);
		params.add("client_secret", lineClientSecret);
		params.add("redirect_uri", baseUrl + lineRedirectUri);
		params.add("code", code);

		String requestUri = env.getProperty("social.line.url.token");
		if (requestUri == null) {
			throw new BaseException(API_INVALID_HOST);
		}
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

		Gson gson = new Gson();
		if (response.getStatusCode() == HttpStatus.OK) {
			return gson.fromJson(response.getBody(), LineAccount.GetLineTokenRes.class);
		}
		throw new BaseException(API_FAILED_REQUEST);
	}

	@ResponseBody
	@GetMapping("")
	public BaseResponse<LineAccount.getLineAccountInfoResDto> getLineAccount() {
		try {
			String lineAccessToken = getLineAccessToken();

			return new BaseResponse<>(getLineAccountInfo(lineAccessToken));

		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	public String getLineAccessToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("LINE-ACCESS-TOKEN");
	}

	public LineAccount.getLineAccountInfoResDto getLineAccountInfo(String lineAccessToken) throws BaseException {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			RestTemplate restTemplate = new RestTemplate();
			String requestUri = env.getProperty("social.line.url.profile");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("id_token", lineAccessToken);
			params.add("client_id", lineClientId);

			if (requestUri == null) {
				throw new BaseException(API_INVALID_HOST);
			}
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				LineAccount.getLineAccountInfoDto resDto = gson.fromJson(response.getBody(),
					LineAccount.getLineAccountInfoDto.class);
				return new LineAccount.getLineAccountInfoResDto(resDto.getSub(), resDto.getEmail());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new BaseException(API_IS_EXPIRED_LINE_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);
	}

	@PostMapping("/sign-up")
	public BaseResponse<LineAccount.PostSignInLineAccountResDto> CreateLineAccount(
		@RequestBody LineAccount.PostLineAccountReqDto postLineAccountReq) throws
		BaseException {
		String lineAccessToken = getLineAccessToken();
		try {
			LineAccount.getLineAccountInfoResDto lineAccount =
				getLineAccountInfo(lineAccessToken);
			if (lineAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_LINE_ACCOUNT);
			String email = lineAccount.getEmail();
			if (email == null) {
				throw new BaseException(SOCIAL_MUST_AGREE_EMAIL);
			}
			String pwd;
			try {
				pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postLineAccountReq.getPassword()); // 암호화코드
				postLineAccountReq.setPassword(pwd);
			} catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
				throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
			}

			LineAccount.PostSignInLineAccountResDto postSignInLineAccountResDto =
				lineAccountService.createLineAccount(LineAccount.PostSignInLineAccountReqDto.builder()
					.email(lineAccount.getEmail())
					.password(postLineAccountReq.getPassword())
					.socialLoginIdx(lineAccount.getId()).build());

			return new BaseResponse<>(postSignInLineAccountResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PostMapping("/log-in")
	public BaseResponse<LineAccount.PostLineLoginResDto> logInByline() throws
		BaseException {
		String lineAccessToken = getLineAccessToken();
		try {
			LineAccount.getLineAccountInfoResDto lineAccount =
				getLineAccountInfo(lineAccessToken);
			if (lineAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_LINE_ACCOUNT);
			if (lineAccountProvider.checkLineAccountByEmail(lineAccount.getEmail()) == 0) {
				throw new BaseException(SOCIAL_CANNOT_FIND_LINE_ACCOUNT);
			}
			LineAccount.GetLineAccountResDto getLineAccountResDto =
				lineAccountProvider.getLineAccountById(lineAccount.getId());

			int accountIdx = getLineAccountResDto.getAccountIdx();
			String jwt = jwtService.createJwt(accountIdx);
			return new BaseResponse<>(new LineAccount.PostLineLoginResDto(accountIdx, jwt));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}
