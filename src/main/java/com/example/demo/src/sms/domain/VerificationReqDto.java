package com.example.demo.src.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationReqDto {
	private String to;
}