package com.example.demo.src.sms.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendVerificationNumberResDto {
	private String verificationNumber;
	private String statusCode;
	private String statusName;
	private String requestId;
	private String requestTime;
}