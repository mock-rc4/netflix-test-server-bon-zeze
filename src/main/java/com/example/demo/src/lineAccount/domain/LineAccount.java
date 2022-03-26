package com.example.demo.src.lineAccount.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
public class LineAccount {

	@Data
	@NoArgsConstructor
	public static class getLineAccountInfoDto {
		private String iss;
		private String sub;
		private String aud;
		private int exp;
		private int iat;
		private int auth_time;
		private String nonce;
		private String[] amr;
		private String name;
		private String picture;
		private String email;
	}

	@Setter
	@AllArgsConstructor
	@Getter
	@ToString
	@NoArgsConstructor
	public static class getLineAccountInfoResDto {
		private String id;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetLineAccountResDto {
		private int accountIdx;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PostLineLoginReqDto {
		private String accessToken;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostLineLoginResDto {
		private int accountIdx;
		private String jwt;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostLineLogoutResDto {
		private String access_token;
		private String result;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostLineAccountReqDto {
		private String password;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostSignInLineAccountReqDto {
		private final String socialLoginType = "Line";
		private String password;
		private String email;
		private String socialLoginIdx;
	}

	@Getter // 해당 클래스에 대한 접근자 생성
	// @Setter // 해당 클래스에 대한 설정자 생성
	// @AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성
	/**
	 * Res.java: From Server To Client
	 * 하나 또는 복수개의 회원정보 조회 요청(Get Request)의 결과(Respone)를 보여주는 데이터의 형태
	 *
	 * GetUserRes는 클라이언트한테 response줄 때 DTO고
	 * User 클래스는 스프링에서 사용하는 Objec이다.
	 */
	public static class GetLineTokenRes {
		private String token_type;
		private String access_token;
		private Integer expires_in;
		private String id_token;
		private String refresh_token;
		private String scope;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostSignInLineAccountResDto {
		private int accountIdx;
		private String jwt;
	}

}