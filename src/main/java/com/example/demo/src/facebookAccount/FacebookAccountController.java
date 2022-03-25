package com.example.demo.src.facebookAccount;

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
import com.example.demo.src.facebookAccount.domain.FacebookAccount;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/facebook-accounts")
public class FacebookAccountController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final FacebookAccountService facebookAccountService;
	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final FacebookAccountProvider facebookAccountProvider;

	@Value("${url.base}")
	private String baseUrl;

	@Value("${social.facebook.client-id}")
	private String facebookClientId;

	@Value("${social.facebook.client-secret}")
	private String facebookClientSecret;

	@Value("${social.facebook.redirect}")
	private String facebookRedirectUri;

	private final Environment env;

	public FacebookAccountController(FacebookAccountService facebookAccountService, JwtService jwtService,
		FacebookAccountProvider facebookAccountProvider, Environment env) {
		this.facebookAccountService = facebookAccountService;
		this.jwtService = jwtService;
		this.facebookAccountProvider = facebookAccountProvider;
		this.env = env;
	}

	// 네이버 로그인창 URL 을 가져오는 API
	@RequestMapping(value = "auth-url")
	public @ResponseBody
	BaseResponse<String> getFacebookAuthUrl() throws Exception {
		String reqUrl = env.getProperty("social.facebook.url.login")
			+ "?response_type=code"
			+ "&client_id=" + facebookClientId
			+ "&redirect_uri="
			+ "localhost:9000" + facebookRedirectUri
			+ "&state=" + makeRandomNumber(); // 난수 생성
		return new BaseResponse<>(reqUrl);
	}

	private String makeRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public FacebookAccount.GetFacebookTokenResDto getFacebookTokenInfo(String code) throws BaseException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		RestTemplate restTemplate = new RestTemplate();
		//
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", facebookClientId);
		params.add("client_secret", facebookClientSecret);
		params.add("code", code);
		params.add("redirect_uri", "auth-url");

		String requestUri = env.getProperty("social.facebook.url.token");
		try {
			if (requestUri == null) {
				throw new BaseException(API_INVALID_HOST);
			}
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(requestUri, request, String.class);

			Gson gson = new Gson();
			if (response.getStatusCode() == HttpStatus.OK) {
				return gson.fromJson(response.getBody(), FacebookAccount.GetFacebookTokenResDto.class);
			}
		} catch (BaseException exception) {
			logger.error(exception.toString());
			throw new BaseException(API_IS_EXPIRED_NAVER_ACCESS_TOKEN);
		}
		throw new BaseException(API_FAILED_REQUEST);

	}

	// facebookAccount에 접근 가능한 facebook Access Token을 반환
	@ResponseBody
	@GetMapping("/redirect")
	public BaseResponse<String> redirectFacebook(@RequestParam() String code) throws BaseException {

		return new BaseResponse<>(code);
		// FacebookAccount.GetFacebookTokenResDto getFacebookTokenResDto = getFacebookTokenInfo(code);
		// return new BaseResponse<>(getFacebookTokenResDto.getAccess_token());
	}
}