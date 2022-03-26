package com.example.demo.src.googleAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.googleAccount.domain.GoogleAccount;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/google-accounts")
public class GoogleAccountController {
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final GoogleAccountService googleAccountService;
	@Autowired
	private final GoogleAccountProvider googleAccountProvider;

	private final ConfigUtils configUtils;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	GoogleAccountController(JwtService jwtService,
		GoogleAccountService googleAccountService, ConfigUtils configUtils,
		GoogleAccountProvider googleAccountProvider) throws BaseException {
		this.jwtService = jwtService;
		this.googleAccountService = googleAccountService;
		this.googleAccountProvider = googleAccountProvider;
		this.configUtils = configUtils;
	}

	@GetMapping(value = "auth-url")
	public BaseResponse<String> getGoogleAuthUrl() {
		return new BaseResponse<>(configUtils.googleInitUrl());
	}

	public GoogleAccount.GoogleLoginResponse getGoogleTokenInfo(String code) throws BaseException {
		try {
			GoogleAccount.GoogleLoginRequest requestParams = GoogleAccount.GoogleLoginRequest.builder()
				.clientId(configUtils.getGoogleClientId())
				.clientSecret(configUtils.getGoogleSecret())
				.code(code)
				.redirectUri(configUtils.getGoogleRedirectUri())
				.grantType("authorization_code")
				.build();
			String requestUri = configUtils.getGoogleAuthUrl();
			if (requestUri == null) {
				throw new BaseException(API_INVALID_HOST);
			}

			// Http Header 설정
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GoogleAccount.GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams,
				headers);
			ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(
				requestUri + "/token", httpRequestEntity, String.class);

			// ObjectMapper를 통해 String to Object로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)

			return objectMapper.readValue(
				apiResponseJson.getBody(),
				new TypeReference<GoogleAccount.GoogleLoginResponse>() {
				});
		} catch (BaseException exception) {
			logger.error(exception.toString());
			throw new BaseException(API_FAILED_REQUEST);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(API_IS_EXPIRED_GOOGLE_ACCESS_TOKEN);
		}
	}

	// GoogleAccount 접근 가능한 Google Access Token을 반환
	@ResponseBody
	@GetMapping("/redirect")
	public BaseResponse<String> redirectGoogle(@RequestParam() String code) throws BaseException {
		try {
			GoogleAccount.GoogleLoginResponse resDto = getGoogleTokenInfo(code);
			return new BaseResponse<>(resDto.getIdToken());
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	//
	public GoogleAccount.getGoogleAccountInfoResDto getGoogleAccountInfo(String googleAccessToken) throws
		BaseException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();

			String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo")
				.queryParam("id_token", googleAccessToken)
				.toUriString();
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				GoogleAccount.getGoogleAccountInfoDto resDto = gson.fromJson(response.getBody(),
					GoogleAccount.getGoogleAccountInfoDto.class);
				return new GoogleAccount.getGoogleAccountInfoResDto(resDto.getSub(), resDto.getEmail());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);
	}

	// 구글 계정 조회
	@ResponseBody
	@GetMapping("")
	public BaseResponse<GoogleAccount.getGoogleAccountInfoResDto> getGoogleAccount() {
		try {
			String googleAccessToken = getGoogleAccessToken();
			GoogleAccount.getGoogleAccountInfoResDto googleAccountLoginDto = getGoogleAccountInfo(googleAccessToken);
			return new BaseResponse<>(googleAccountLoginDto);

		} catch (BaseException exception) {

			return new BaseResponse<>(exception.getStatus());
		}
	}

	public String getGoogleAccessToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("GOOGLE-ACCESS-TOKEN");
	}

	@PostMapping("/sign-up")
	public BaseResponse<GoogleAccount.PostSignInGoogleAccountResDto> CreateGoogleAccount(
		@RequestBody GoogleAccount.PostGoogleAccountReqDto postGoogleAccountReq) throws
		BaseException {
		String GoogleAccessToken = getGoogleAccessToken();
		try {
			GoogleAccount.getGoogleAccountInfoResDto googleAccount =
				getGoogleAccountInfo(GoogleAccessToken);
			if (googleAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			String email = googleAccount.getEmail();
			if (email == null) {
				throw new BaseException(SOCIAL_MUST_AGREE_EMAIL);
			}
			String pwd;
			try {
				pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postGoogleAccountReq.getPassword()); // 암호화코드
				postGoogleAccountReq.setPassword(pwd);
			} catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
				throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
			}

			GoogleAccount.PostSignInGoogleAccountResDto postSignInGoogleAccountResDto =
				googleAccountService.createGoogleAccount(GoogleAccount.PostSignInGoogleAccountReqDto.builder()
					.email(googleAccount.getEmail())
					.password(postGoogleAccountReq.getPassword())
					.socialLoginIdx(googleAccount.getId()).build());

			return new BaseResponse<>(postSignInGoogleAccountResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PostMapping("/log-in")
	public BaseResponse<GoogleAccount.PostGoogleLoginResDto> logInByGoogle() throws
		BaseException {
		String googleAccessToken = getGoogleAccessToken();
		try {
			GoogleAccount.getGoogleAccountInfoResDto googleAccount =
				getGoogleAccountInfo(googleAccessToken);
			if (googleAccount == null)
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			if (googleAccountProvider.checkGoogleAccountByEmail(googleAccount.getEmail()) == 0) {
				throw new BaseException(SOCIAL_CANNOT_FIND_ACCOUNT);
			}
			GoogleAccount.GetGoogleAccountResDto getGoogleAccountResDto =
				googleAccountProvider.getGoogleAccountById(googleAccount.getId());

			int accountIdx = getGoogleAccountResDto.getAccountIdx();
			String jwt = jwtService.createJwt(accountIdx);
			return new BaseResponse<>(new GoogleAccount.PostGoogleLoginResDto(accountIdx, jwt));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}
