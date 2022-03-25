package com.example.demo.src.googleAccount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class GoogleAccount {

	private com.example.demo.src.naverAccount.domain.NaverAccount.Response response;

	@Getter
	@ToString
	public static class Response {
		private String id;
		private String email;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class GetGoogleTokenResDto {
		private String access_token;
		private String token_type;
		private Integer expires_in;
	}
}
