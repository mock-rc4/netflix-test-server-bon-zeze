package com.example.demo.src.GoogleAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.Random;

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
import com.example.demo.src.googleAccount.domain.GoogleAccount;
import com.example.demo.src.googleAccount.GoogleAccountProvider;
import com.example.demo.src.googleAccount.GoogleAccountService;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/Google-accounts")
public class GoogleAccountController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final GoogleAccountService googleAccountService;
	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final GoogleAccountProvider googleAccountProvider;

	@Value("${url.base}")
	private String baseUrl;

	@Value("${social.Google.client-id}")
	private String GoogleClientId;

	@Value("${social.Google.client-secret}")
	private String GoogleClientSecret;

	@Value("${social.Google.redirect}")
	private String GoogleRedirectUri;

	private final Environment env;

	public GoogleAccountController(GoogleAccountService googleAccountService, JwtService jwtService,
		GoogleAccountProvider googleAccountProvider, Environment env) {
		this.googleAccountService = googleAccountService;
		this.jwtService = jwtService;
		this.googleAccountProvider = googleAccountProvider;
		this.env = env;
	}

	// 네이버 로그인창 URL 을 가져오는 API
	@RequestMapping(value = "auth-url")
	public @ResponseBody
	BaseResponse<String> getGoogleAuthUrl() throws Exception {
		String reqUrl = env.getProperty("social.Google.url.login")
			+ "?response_type=code"
			+ "&client_id=" + GoogleClientId
			+ "&redirect_uri="
			+ "localhost:9000" + GoogleRedirectUri
			+ "&state=" + makeRandomNumber(); // 난수 생성
		return new BaseResponse<>(reqUrl);
	}

	private String makeRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public GoogleAccount.GetGoogleTokenResDto getGoogleTokenInfo(String code) throws BaseException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		RestTemplate restTemplate = new RestTemplate();
		//
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", GoogleClientId);
		params.add("redirect_uri", "https://localhost:9000/Google-accounts/redirect/");
		params.add("client_secret", GoogleClientSecret);
		params.add("code", code);


		String requestUri = env.getProperty("social.Google.url.token");
		try {
			if (requestUri == null) {
				throw new BaseException(API_INVALID_HOST);
			}
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				return gson.fromJson(response.getBody(), GoogleAccount.GetGoogleTokenResDto.class);
			}
		} catch (BaseException exception) {
			logger.error(exception.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);

	}

	// GoogleAccount에 접근 가능한 Google Access Token을 반환
	@ResponseBody
	@GetMapping("/redirect")
	public BaseResponse<String>
	redirectGoogle(@RequestParam() String code) throws BaseException {

		GoogleAccount.GetGoogleTokenResDto getGoogleTokenResDto = getGoogleTokenInfo(code);
		return new BaseResponse<>(getGoogleTokenResDto.getAccess_token());
	}

}
