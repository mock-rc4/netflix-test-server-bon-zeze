package com.example.demo.src.naverAccount;

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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import com.example.demo.src.naverAccount.domain.NaverAccount;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/naver-accounts")
public class NaverAccountController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final NaverAccountService naverAccountService;
	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final NaverAccountProvider naverAccountProvider;

	@Value("${url.base}")
	private String baseUrl;

	@Value("${social.naver.client-id}")
	private String naverClientId;

	@Value("${social.naver.client-secret}")
	private String naverClientSecret;

	@Value("${social.naver.redirect}")
	private String naverRedirectUri;

	private final Environment env;

	public NaverAccountController(NaverAccountService naverAccountService, JwtService jwtService,
		NaverAccountProvider naverAccountProvider, Environment env) {
		this.naverAccountService = naverAccountService;
		this.jwtService = jwtService;
		this.naverAccountProvider = naverAccountProvider;
		this.env = env;
	}

	// 네이버 로그인창 URL 을 가져오는 API
	@RequestMapping(value = "auth-url")
	public @ResponseBody
	BaseResponse<String> getNaverAuthUrl() throws Exception {
		String reqUrl = env.getProperty("social.naver.url.login")
			+ "?response_type=code"
			+ "&client_id=" + naverClientId
			+ "&redirect_uri="
			+ baseUrl + naverRedirectUri
			+ "&state=" + makeRandomNumber(); // 난수 생성
		return new BaseResponse<>(reqUrl);
	}

	private String makeRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public NaverAccount.GetNaverTokenResDto getNaverTokenInfo(String code, String state) throws BaseException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		RestTemplate restTemplate = new RestTemplate();
		//
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", naverClientId);
		params.add("client_secret", naverClientSecret);
		params.add("code", code);
		params.add("state", state);

		String requestUri = env.getProperty("social.naver.url.token");
		try {
			if (requestUri == null) {
				throw new BaseException(API_INVALID_HOST);
			}
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				return gson.fromJson(response.getBody(), NaverAccount.GetNaverTokenResDto.class);
			}
		} catch (BaseException exception) {
			logger.error(exception.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);

	}

	// NaverAccount에 접근 가능한 Naver Access Token을 반환
	@ResponseBody
	@GetMapping("/redirect")
	public BaseResponse<String> redirectNaver(@RequestParam() String code,

		@RequestParam() String state) throws BaseException {

		try {
			NaverAccount.GetNaverTokenResDto getNaverTokenResDto = getNaverTokenInfo(code, state);
			return new BaseResponse<>(getNaverTokenResDto.getAccess_token());
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	// 네이버 계정 조회
	@ResponseBody
	@GetMapping("")
	public BaseResponse<NaverAccount.Response> getNaverAccount() {
		try {
			String naverAccessToken = getNaverAccessToken();
			NaverAccount naverAccount = getNaverAccountInfo(naverAccessToken);
			return new BaseResponse<>(naverAccount.getResponse());

		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	public NaverAccount getNaverAccountInfo(String naverAccessToken) throws BaseException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + naverAccessToken);
		String requestUri = env.getProperty("social.naver.url.profile");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		if (requestUri == null) {
			throw new BaseException(API_INVALID_HOST);
		}

		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				return gson.fromJson(response.getBody(), NaverAccount.class);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);
	}

	@PostMapping("/log-in")
	public BaseResponse<NaverAccount.PostNaverLoginResDto> logInByNaver() throws
		BaseException {
		String naverAccessToken = getNaverAccessToken();
		try {
			NaverAccount naverAccount =
				getNaverAccountInfo(naverAccessToken);
			if (naverAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			if (naverAccountProvider.checkNaverAccountByEmail(naverAccount.getResponse().getEmail()) == 0) {
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			}
			NaverAccount.GetNaverAccountResDto getNaverAccountResDto =
				naverAccountProvider.getNaverAccountById(naverAccount.getResponse().getId());

			int accountIdx = getNaverAccountResDto.getAccountIdx();
			String jwt = jwtService.createJwt(accountIdx);
			return new BaseResponse<>(new NaverAccount.PostNaverLoginResDto(accountIdx, jwt));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PostMapping("/sign-up")
	public BaseResponse<NaverAccount.PostSignInNaverAccountResDto> CreateNaverAccount(
		@RequestBody NaverAccount.PostNaverAccountReqDto postNaverAccountReq) throws
		BaseException {
		String naverAccessToken = getNaverAccessToken();
		try {
			NaverAccount naverAccount =
				getNaverAccountInfo(naverAccessToken);
			if (naverAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			String email = naverAccount.getResponse().getEmail();
			if (email == null) {
				throw new BaseException(SOCIAL_MUST_AGREE_EMAIL);
			}

			String pwd;
			try {
				pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postNaverAccountReq.getPassword());
				postNaverAccountReq.setPassword(pwd);
			} catch (Exception ignored) {
				throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
			}

			NaverAccount.PostSignInNaverAccountResDto postSignInNaverAccountResDto =
				naverAccountService.createNaverAccount(NaverAccount.PostSignInNaverAccountReqDto.builder()
					.email(naverAccount.getResponse().getEmail())
					.password(postNaverAccountReq.getPassword())
					.socialLoginIdx(naverAccount.getResponse().getId()).build());

			return new BaseResponse<>(postSignInNaverAccountResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@ResponseBody
	@GetMapping("/log-out")
	public BaseResponse<String> logOutNaver() {
		try {
			String naverAccessToken = getNaverAccessToken();
			NaverAccount.PostNaverLogoutResDto postNaverLogoutResDto = logOutNaverService(naverAccessToken);
			return new BaseResponse<>(postNaverLogoutResDto.getResult());

		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	public NaverAccount.PostNaverLogoutResDto logOutNaverService(String naverAccessToken) throws BaseException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		RestTemplate restTemplate = new RestTemplate();
		//
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "delete");
		params.add("client_id", naverClientId);
		params.add("client_secret", naverClientSecret);
		params.add("service_provider", "NAVER");
		params.add("access_token", naverAccessToken);

		String requestUri = env.getProperty("social.naver.url.token");
		if (requestUri == null) {
			throw new BaseException(API_INVALID_HOST);
		}

		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				return gson.fromJson(response.getBody(), NaverAccount.PostNaverLogoutResDto.class);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);

	}

	// Header에서 NAVER-ACCESS-TOKEN 추출
	// @return String
	//
	public String getNaverAccessToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("NAVER-ACCESS-TOKEN");
	}
}