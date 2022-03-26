package com.example.demo.src.googleAccount.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
public class GoogleAccount {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetGoogleTokenResDto {
		private String access_token;
		private String token_type;
		private Integer expires_in;
	}

	// 일회성 토큰을 받은 후, 해당 일회성 토큰을 가지고 AccessToken을 얻기 위한 Request VO
	@Data
	@Builder
	public static class GoogleLoginRequest {
		private String clientId;    // 애플리케이션의 클라이언트 ID
		private String redirectUri; // Google 로그인 후 redirect 위치
		private String clientSecret;    // 클라이언트 보안 비밀
		private String responseType;    // Google OAuth 2.0 엔드포인트가 인증 코드를 반환하는지 여부
		private String scope;   // OAuth 동의범위
		private String code;
		private String accessType;  // 사용자가 브라우저에 없을 때 애플리케이션이 액세스 토큰을 새로 고칠 수 있는지 여부
		private String grantType;
		private String state;
		private String includeGrantedScopes;    // 애플리케이션이 컨텍스트에서 추가 범위에 대한 액세스를 요청하기 위해 추가 권한 부여를 사용
		private String loginHint;   // 애플리케이션이 인증하려는 사용자를 알고 있는 경우 이 매개변수를 사용하여 Google 인증 서버에 힌트를 제공
		private String prompt;  // default: 처음으로 액세스를 요청할 때만 사용자에게 메시지가 표시
	}

	// 일회성 토큰을 통해 얻은 Response VO
	// idToken을 전달해 사용자 정보를 얻을 예정

	@Data
	@NoArgsConstructor
	public static class GoogleLoginResponse {
		private String accessToken; // 애플리케이션이 Google API 요청을 승인하기 위해 보내는 토큰
		private String expiresIn;   // Access Token의 남은 수명
		private String refreshToken;    // 새 액세스 토큰을 얻는 데 사용할 수 있는 토큰
		private String scope;
		private String tokenType;   // 반환된 토큰 유형(Bearer 고정)
		private String idToken;
	}

	@Data
	@NoArgsConstructor
	public static class getGoogleAccountInfoDto {

		private String iss;
		private String azp;
		private String aud;
		private String sub;
		private String email;
		private String emailVerified;
		private String atHash;
		private String name;
		private String picture;
		private String givenName;
		private String familyName;
		private String locale;
		private String iat;
		private String exp;
		private String alg;
		private String kid;
		private String typ;

	}

	@Setter
	@AllArgsConstructor
	@Getter
	@ToString
	@NoArgsConstructor
	public static class getGoogleAccountInfoResDto {
		private String id;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetGoogleAccountResDto {
		private int accountIdx;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PostGoogleLoginReqDto {
		private String accessToken;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostGoogleLoginResDto {
		private int accountIdx;
		private String jwt;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostGoogleLogoutResDto {
		private String access_token;
		private String result;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostGoogleAccountReqDto {
		private String password;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostSignInGoogleAccountReqDto {
		private final String socialLoginType = "Google";
		private String password;
		private String email;
		private String socialLoginIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostSignInGoogleAccountResDto {
		private int accountIdx;
		private String jwt;
	}
}

