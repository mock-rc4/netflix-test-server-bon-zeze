package com.example.demo.src.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.demo.src.email.domain.EmailDto;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(EmailDto mail) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getEmailAddress());
		//        message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
		message.setSubject(mail.getTitle());
		message.setText(mail.getContent());

		mailSender.send(message);
	}
}