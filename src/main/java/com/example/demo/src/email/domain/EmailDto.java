package com.example.demo.src.email.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailDto {

	private String emailAddress;
	private String title;
	private String content;
	private String result;

	public EmailDto() {
	}
}