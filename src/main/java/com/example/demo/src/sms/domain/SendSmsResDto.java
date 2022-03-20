package com.example.demo.src.sms.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendSmsResDto {
	private String statusCode;
	private String statusName;
	private String requestId;
	private String requestTime;
}