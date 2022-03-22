package com.example.demo.src.naverAccount.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
public class NaverAccount {

	private Response response;

	@Getter
	@ToString
	public static class Response {
		private String id;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetNaverAccountResDto {
		private int accountIdx;
		private String email;
	}


	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetNaverTokenResDto {
		private String access_token;
		private String refresh_token;
		private String token_type;
		private Integer expires_in;
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PostNaverLoginReqDto {
		private String accessToken;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostNaverLoginResDto {
		private int accountIdx;
		private String jwt;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostNaverLogoutResDto {
		private String access_token;
		private String result;
	}



	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostNaverAccountReqDto {
		private String password;
	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class PostSignInNaverAccountReqDto {
		private final String socialLoginType = "Naver";
		private String password;
		private String email;
		private String socialLoginIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class PostSignInNaverAccountResDto {
		private int accountIdx;
		private String jwt;
	}

}