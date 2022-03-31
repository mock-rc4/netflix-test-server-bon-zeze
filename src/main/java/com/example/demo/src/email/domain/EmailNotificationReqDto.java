package com.example.demo.src.email.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationReqDto {
	private String emailAddress;
	private String title;
	private String content;
}
