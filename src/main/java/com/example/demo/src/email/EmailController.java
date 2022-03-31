package com.example.demo.src.email;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.email.domain.EmailDto;
import com.example.demo.src.email.domain.EmailNotificationReqDto;
import com.example.demo.src.email.domain.VerificationReqDto;

@RestController
@RequestMapping("/email-services")
public class EmailController {

	@Autowired
	private EmailService emailService;

	private final Environment env;

	public EmailController(Environment env) {
		this.env = env;
	}

	@PostMapping("/email-notification")
	public BaseResponse<EmailDto> SendEmailNotification(@RequestBody EmailNotificationReqDto reqDto) {
		if (!isRegexEmail(reqDto.getEmailAddress())){
			return new BaseResponse<>(MESSAGE_INVALID_EMAIL);
		}
		if (reqDto.getTitle().isEmpty()) {
			return new BaseResponse<>(MESSAGE_NO_TITLE);
		}
		if (reqDto.getContent().isEmpty()) {
			return new BaseResponse<>(MESSAGE_NO_ANY_CONTENT);
		}

		EmailDto emailReqDto = new EmailDto();

		emailReqDto.setEmailAddress(reqDto.getEmailAddress());
		emailReqDto.setTitle(reqDto.getTitle());
		emailReqDto.setContent(reqDto.getContent());
		emailReqDto.setResult("success");
		emailService.sendEmail(emailReqDto);
		return new BaseResponse<>(emailReqDto);
	}

	private String makeRandomNumber() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	@ResponseBody
	@PostMapping("/verification-number")
	public BaseResponse<EmailDto> smsVerificationNumberService(@RequestBody
		VerificationReqDto reqDto) {

		if (!isRegexEmail(reqDto.getEmailAddress())){
			return new BaseResponse<>(MESSAGE_INVALID_EMAIL);
		}

		String title = "이메일 인증 코드 입력";
		String verificationNumber = makeRandomNumber();
		String verificationMessage = String.format("인증 번호는 %s 입니다.", verificationNumber);

		EmailDto emailReqDto = new EmailDto();
		emailReqDto.setTitle(title);
		emailReqDto.setContent(verificationMessage);
		emailReqDto.setEmailAddress(reqDto.getEmailAddress());
		emailReqDto.setResult("success");

		emailService.sendEmail(emailReqDto);
		return new BaseResponse<>(emailReqDto);
		}
	}







