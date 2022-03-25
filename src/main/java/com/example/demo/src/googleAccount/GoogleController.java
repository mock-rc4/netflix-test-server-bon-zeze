package com.example.demo.src.GoogleAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.googleAccount.ConfigUtils;
import com.example.demo.src.googleAccount.domain.GoogleAccount;
import com.example.demo.src.googleAccount.GoogleAccountProvider;
import com.example.demo.src.googleAccount.GoogleAccountService;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/google")
public class GoogleController {

	private final ConfigUtils configUtils;

	GoogleController(ConfigUtils configUtils) {
		this.configUtils = configUtils;
	}

	@GetMapping(value = "/login")
	public ResponseEntity<Object> moveGoogleInitUrl() {
		String authUrl = configUtils.googleInitUrl();
		URI redirectUri = null;
		try {
			redirectUri = new URI(authUrl);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(redirectUri);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/login/redirect")
	public ResponseEntity<GoogleAccount.GoogleLoginDto> redirectGoogleLogin(
		@RequestParam(value = "code") String authCode
	) {
		// HTTP 통신을 위해 RestTemplate 활용
		RestTemplate restTemplate = new RestTemplate();
		GoogleAccount.GoogleLoginRequest requestParams = GoogleAccount.GoogleLoginRequest.builder()
			.clientId(configUtils.getGoogleClientId())
			.clientSecret(configUtils.getGoogleSecret())
			.code(authCode)
			.redirectUri(configUtils.getGoogleRedirectUri())
			.grantType("authorization_code")
			.build();

		try {
			// Http Header 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GoogleAccount.GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
			ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);

			// ObjectMapper를 통해 String to Object로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
			GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});

			// 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
			String jwtToken = googleLoginResponse.getIdToken();

			// JWT Token을 전달해 JWT 저장된 사용자 정보 확인
			String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

			String resultJson = restTemplate.getForObject(requestUrl, String.class);

			if(resultJson != null) {
				GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});

				return ResponseEntity.ok().body(userInfoDto);
			}
			else {
				throw new Exception("Google OAuth failed!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body(null);
	}
}


//
// @RestController
// @RequestMapping("/google-accounts")
// public class GoogleAccountController {
//
// 	final Logger logger = LoggerFactory.getLogger(this.getClass());
// 	@Autowired
// 	private final GoogleAccountService googleAccountService;
// 	@Autowired
// 	private final JwtService jwtService;
//
// 	@Autowired
// 	private final GoogleAccountProvider googleAccountProvider;
//
// 	@Value("${url.base}")
// 	private String baseUrl;
//
// 	@Value("${social.google.client-id}")
// 	private String googleClientId;
//
// 	@Value("${social.google.client-secret}")
// 	private String googleClientSecret;
//
// 	@Value("${social.google.redirect}")
// 	private String googleRedirectUri;
//
// 	@Value("${social.google.url.login}")
// 	private String googleLoginUrl;
//
// 	private final Environment env;
//
//
//
// 	public GoogleAccountController(GoogleAccountService googleAccountService, JwtService jwtService,
// 		GoogleAccountProvider googleAccountProvider, Environment env) {
// 		this.googleAccountService = googleAccountService;
// 		this.jwtService = jwtService;
// 		this.googleAccountProvider = googleAccountProvider;
// 		this.env = env;
// 	}
//
// 	// 네이버 로그인창 URL 을 가져오는 API
// 	@RequestMapping(value = "auth-url")
// 	public @ResponseBody
// 	BaseResponse<String> getGoogleAuthUrl() throws Exception {
//
// 		Map<String, Object> params = new HashMap<>();
// 		params.put("client_id", googleClientId);
// 		params.put("redirect_uri", googleRedirectUri);
// 		params.put("response_type", "code");
// 		params.put("scope", "email%20profile%20openid");
//
// 		String paramStr = params.entrySet().stream()
// 			.map(param -> param.getKey() + "=" + param.getValue())
// 			.collect(Collectors.joining("&"));
//
// 		return new BaseResponse<>(googleLoginUrl
// 			+ "/o/oauth2/v2/auth"
// 			+ "?"
// 			+ paramStr);
// 		// String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUri
// 		// 	+ "&response_type=code&scope=email%20profile%20openid&access_type=offline";
// 		// return new BaseResponse<>(reqUrl);
// 	}
//
// 	@GetMapping(value = "/login")
// 	public ResponseEntity<Object> moveGoogleInitUrl() {
// 		String authUrl = configUtils.googleInitUrl();
// 		URI redirectUri = null;
// 		try {
// 			redirectUri = new URI(authUrl);
// 			HttpHeaders httpHeaders = new HttpHeaders();
// 			httpHeaders.setLocation(redirectUri);
// 			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
// 		} catch (URISyntaxException e) {
// 			e.printStackTrace();
// 		}
//
// 		return ResponseEntity.badRequest().build();
// 	}
//
//
// 	@GetMapping(value = "/login/redirect")
// 	public ResponseEntity<GoogleLoginDto> redirectGoogleLogin(
// 		@RequestParam(value = "code") String authCode
// 	) {
// 		// HTTP 통신을 위해 RestTemplate 활용
// 		RestTemplate restTemplate = new RestTemplate();
// 		GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
// 			.clientId(configUtils.getGoogleClientId())
// 			.clientSecret(configUtils.getGoogleSecret())
// 			.code(authCode)
// 			.redirectUri(configUtils.getGoogleRedirectUri())
// 			.grantType("authorization_code")
// 			.build();
//
// 		try {
// 			// Http Header 설정
// 			HttpHeaders headers = new HttpHeaders();
// 			headers.setContentType(MediaType.APPLICATION_JSON);
// 			HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
// 			ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);
//
// 			// ObjectMapper를 통해 String to Object로 변환
// 			ObjectMapper objectMapper = new ObjectMapper();
// 			objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
// 			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
// 			GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});
//
// 			// 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
// 			String jwtToken = googleLoginResponse.getIdToken();
//
// 			// JWT Token을 전달해 JWT 저장된 사용자 정보 확인
// 			String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();
//
// 			String resultJson = restTemplate.getForObject(requestUrl, String.class);
//
// 			if(resultJson != null) {
// 				GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
//
// 				return ResponseEntity.ok().body(userInfoDto);
// 			}
// 			else {
// 				throw new Exception("Google OAuth failed!");
// 			}
// 		}
// 		catch (Exception e) {
// 			e.printStackTrace();
// 		}
//
// 		return ResponseEntity.badRequest().body(null);
// 	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// 	private String makeRandomNumber() {
// 		Random rnd = new Random();
// 		int number = rnd.nextInt(999999);
// 		return String.format("%06d", number);
// 	}
//
// 	public GoogleAccount.GetGoogleTokenResDto getGoogleTokenInfo(String code) throws BaseException {
//
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
// 		RestTemplate restTemplate = new RestTemplate();
// 		//
// 		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
// 		params.add("client_id", GoogleClientId);
// 		params.add("redirect_uri", "https://localhost:9000/Google-accounts/redirect/");
// 		params.add("client_secret", GoogleClientSecret);
// 		params.add("code", code);
//
//
// 		String requestUri = env.getProperty("social.Google.url.token");
// 		try {
// 			if (requestUri == null) {
// 				throw new BaseException(API_INVALID_HOST);
// 			}
// 			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
// 			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);
//
// 			Gson gson = new Gson();
// 			if (response.getStatusCode() == HttpStatus.OK) {
// 				return gson.fromJson(response.getBody(), GoogleAccount.GetGoogleTokenResDto.class);
// 			}
// 		} catch (BaseException exception) {
// 			logger.error(exception.toString());
// 			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
// 		}
// 		throw new BaseException(API_FAILED_REQUEST);
//
// 	}
//
// 	// GoogleAccount에 접근 가능한 Google Access Token을 반환
// 	@ResponseBody
// 	@GetMapping("/redirect")
// 	public BaseResponse<String>
// 	redirectGoogle(@RequestParam() String code) throws BaseException {
//
// 		GoogleAccount.GetGoogleTokenResDto getGoogleTokenResDto = getGoogleTokenInfo(code);
// 		return new BaseResponse<>(getGoogleTokenResDto.getAccess_token());
// 	}
//
// }
