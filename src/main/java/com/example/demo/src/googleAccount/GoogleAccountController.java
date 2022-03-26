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
import com.example.demo.src.googleAccount.domain.GoogleAccount;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/google-accounts")
public class GoogleController {
	@Autowired
	private final JwtService jwtService;

	private final ConfigUtils configUtils;
	final Logger logger = LoggerFactory.getLogger(this.getClass());



	GoogleController(JwtService jwtService, ConfigUtils configUtils) throws BaseException {
		this.jwtService = jwtService;
		this.configUtils = configUtils;
	}

	@GetMapping(value = "auth-url")
	public BaseResponse<String> getGoogleAuthUrl() {
		return new BaseResponse<>(configUtils.googleInitUrl());
	}

	// @GetMapping(value = "/login")
	// public ResponseEntity<Object> moveGoogleInitUrl() {
	// 	String authUrl = configUtils.googleInitUrl();
	// 	URI redirectUri = null;
	// 	try {
	// 		redirectUri = new URI(authUrl);
	// 		HttpHeaders httpHeaders = new HttpHeaders();
	// 		httpHeaders.setLocation(redirectUri);
	// 		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	// 	} catch (URISyntaxException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return ResponseEntity.badRequest().build();
	// }

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
	public GoogleAccount.getGoogleAccountInfoResDto getGoogleAccountInfo(String googleAccessToken) throws BaseException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
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





			// String resultJson = restTemplate.getForObject(requestUrl, String.class);

		// 	if (resultJson != null) {
		// 		GoogleAccount.GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleAccount.GoogleLoginDto>() {
		// 		});
		// 		ResponseEntity<GoogleAccount.GoogleLoginDto> response = ResponseEntity.ok().body(userInfoDto);
		// 		return ResponseEntity.ok().body(userInfoDto);
		// 	} else {
		// 		throw new Exception("Google OAuth failed!");
		// 	}
		// }         catch (Exception e) {
		// 	e.printStackTrace();
		// }
		//
		// return ResponseEntity.badRequest().body(null);
		//
		// 	Gson gson = new Gson();
		// if(resultJson != null) {
		// 	GoogleAccount.GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleAccount.GoogleLoginDto>() {});
		// 	ResponseEntity<GoogleAccount.GoogleLoginDto> response = ResponseEntity.ok().body(userInfoDto);
		// 	if (response.getStatusCode() == HttpStatus.OK){
		// 		return gson.fromJson(String.valueOf(response.getBody()), (Type)GoogleAccount.GoogleLoginDto.class);
		// 	}
		// } }catch (Exception e) {
		// 		logger.error(e.toString());
		// 		throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		// 	}
		// 	throw new BaseException(API_FAILED_REQUEST);


	// 	else {
	// 		throw new Exception("Google OAuth failed!");
	// 	}
	// }
    //     catch (Exception e) {
	// 	e.printStackTrace();
	// }
	//
    //     return ResponseEntity.badRequest().body(null);
	//
	//
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("Authorization", "Bearer " + googleAccessToken);
	// 	String requestUri = configUtils
	//
	//
	// 	restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	// 	if (requestUri == null) {
	// 		throw new BaseException(API_INVALID_HOST);
	// 	}
	//
	// 	try {
	// 		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
	// 		ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);
	//
	// 		Gson gson = new Gson();
	// 		if (response.getStatusCode() == HttpStatus.OK) {
	// 			return gson.fromJson(response.getBody(), NaverAccount.class);
	// 		}
	// 	} catch (Exception e) {
	// 		logger.error(e.toString());
	// 		throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
	// 	}
	// 	throw new BaseException(API_FAILED_REQUEST);




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
	//
	public String getGoogleAccessToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("GOOGLE-ACCESS-TOKEN");
	}

	//
	// @GetMapping(value = "/redirect")
	// public ResponseEntity<GoogleAccount.GoogleLoginDto> redirectGoogleLogin(
	// 	@RequestParam(value = "code") String authCode
	// ) {
	// 	// HTTP 통신을 위해 RestTemplate 활용
	// 	RestTemplate restTemplate = new RestTemplate();
	// 	GoogleAccount.GoogleLoginRequest requestParams = GoogleAccount.GoogleLoginRequest.builder()
	// 		.clientId(configUtils.getGoogleClientId())
	// 		.clientSecret(configUtils.getGoogleSecret())
	// 		.code(authCode)
	// 		.redirectUri(configUtils.getGoogleRedirectUri())
	// 		.grantType("authorization_code")
	// 		.build();
	//
	// 	try {
	// 		// Http Header 설정
	// 		HttpHeaders headers = new HttpHeaders();
	// 		headers.setContentType(MediaType.APPLICATION_JSON);
	// 		HttpEntity<GoogleAccount.GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
	// 		ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(
	// 			configUtils.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);
	//
	// 		// ObjectMapper를 통해 String to Object로 변환
	// 		ObjectMapper objectMapper = new ObjectMapper();
	// 		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	// 		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
	// 		GoogleAccount.GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(),
	// 			new TypeReference<GoogleAccount.GoogleLoginResponse>() {
	// 			});
	//
	// 		// 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
	// 		String jwtToken = googleLoginResponse.getIdToken();
	//
	// 		// JWT Token을 전달해 JWT 저장된 사용자 정보 확인
	// 		String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo")
	// 			.queryParam("id_token", jwtToken)
	// 			.toUriString();
	//
	// 		String resultJson = restTemplate.getForObject(requestUrl, String.class);
	//
	// 		if (resultJson != null) {
	// 			GoogleAccount.GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson,
	// 				new TypeReference<GoogleAccount.GoogleLoginDto>() {
	// 				});
	//
	// 			return ResponseEntity.ok().body(userInfoDto);
	// 		} else {
	// 			throw new Exception("Google OAuth failed!");
	// 		}
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	//
	// 	return ResponseEntity.badRequest().body(null);
	// }
}
