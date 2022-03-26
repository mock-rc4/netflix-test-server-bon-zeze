package com.example.demo.src.googleAccount;

// @Value를 사용하기 위해 @Component 추가
// 해당 클래스를 Bean으로 생성해둠
// 추후 사용할 일이 있을 때, Bean 인스턴스를 꺼내 활용

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
// @Value를 사용하기 위해 @Component 추가
// 해당 클래스를 Bean으로 생성해둠
// 추후 사용할 일이 있을 때, Bean 인스턴스를 꺼내 활용

@Component
public class ConfigUtils {

	private final Environment env;

	ConfigUtils(Environment env) {
		this.env = env;
	}

	@Value("${url.base}")
	private String baseUrl;

	@Value("${social.google.url.auth}")
	private String googleAuthUrl;

	@Value("${social.google.url.login}")
	private String googleLoginUrl;

	@Value("${social.google.redirect}")
	private String googleRedirectUrl;

	@Value("${social.google.client-id}")
	private String googleClientId;

	@Value("${social.google.client-secret}")
	private String googleSecret;

	@Value("${social.google.scope}")
	private String scopes;

	// Google 로그인 URL 생성 로직
	public String googleInitUrl() {
		Map<String, Object> params = new HashMap<>();
		params.put("client_id", getGoogleClientId());
		params.put("redirect_uri", getGoogleRedirectUri());
		params.put("response_type", "code");
		params.put("scope", getScopeUrl());

		String paramStr = params.entrySet().stream()
			.map(param -> param.getKey() + "=" + param.getValue())
			.collect(Collectors.joining("&"));

		return getGoogleLoginUrl()
			+ "/o/oauth2/v2/auth"
			+ "?"
			+ paramStr;
	}

	public String getGoogleAuthUrl() {
		return googleAuthUrl;
	}

	public String getGoogleLoginUrl() {
		return googleLoginUrl;
	}

	public String getGoogleClientId() {
		return googleClientId;
	}

	public String getGoogleRedirectUri() {
		return baseUrl + googleRedirectUrl;
	}

	public String getGoogleSecret() {
		return googleSecret;
	}

	public String getScopeUrl() {
		return scopes.replaceAll(",", "%20");
	}
}
