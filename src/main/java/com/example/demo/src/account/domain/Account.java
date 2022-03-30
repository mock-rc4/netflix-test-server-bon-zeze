package com.example.demo.src.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Account {
	private int accountIdx;
	private String password;
	private String email;
	private String phoneNumber;
	private String membership;
	private String socialLoginIdx;
	private String socialLoginType;
	private int status;


	@Getter
	@Setter
	@AllArgsConstructor
	public static class createReqDto {
		private String password;
		private String email;
		private Boolean personal_information_agreement;
		private Boolean email_subscription_agreement;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class createResDto {
		private int accountIdx;
		private String jwt;
	}

	@Getter
	@AllArgsConstructor
	public static class DeactivateReqDto {
		private int accountIdx;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class getResDto {
		private int accountIdx;
		private String password;
		private String email;
		private String phoneNumber;
		private String membership;
		private String socialLoginIdx;
		private String socialLoginType;
		private String membershipStartDate;

	}

	@Getter
	@AllArgsConstructor
	public static class getAccountsDto {
		private int accountIdx;
		private String email;
		private String phoneNumber;
		private String membership;
	}
}