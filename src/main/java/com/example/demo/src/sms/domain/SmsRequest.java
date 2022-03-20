package com.example.demo.src.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SmsRequest {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class SmsMessageReqDto {
		private String to;
		private String content;
	}

}