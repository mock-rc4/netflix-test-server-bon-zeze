package com.example.demo.src.sms;

import static com.example.demo.config.BaseResponseStatus.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.sms.domain.MessagesReqDto;
import com.example.demo.src.sms.domain.SendSmsResDto;
import com.example.demo.src.sms.domain.SendVerificationNumberResDto;
import com.example.demo.src.sms.domain.SmsRequestDto;
import com.example.demo.src.sms.domain.VerificationReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/sms-services")
public class SmsController {

	@Value("${ncp.access_key}")
	private String accessKey;

	@Value("${ncp.service_id}")
	private String serviceId;

	@Value("${ncp.secret_key}")
	private String secretKey;

	private final Environment env;

	public SmsController(Environment env) {
		this.env = env;
	}

	public String makeSignature(String time) throws
		UnsupportedEncodingException,
		InvalidKeyException,
		NoSuchAlgorithmException {

		String space = " ";                    // one space
		String newLine = "\n";                    // new line
		String method = "POST";                    // method
		String url = "/sms/v2/services/" + serviceId + "/messages";    // url (include query string)
		String timestamp = time;            // current timestamp (epoch)

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

		return encodeBase64String;
	}

	private String makeRandomNumber() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public SendVerificationNumberResDto sendVerificationNumber(String recipientPhoneNumber) throws
		JsonProcessingException,
		UnsupportedEncodingException,
		InvalidKeyException,
		NoSuchAlgorithmException,
		URISyntaxException, BaseException {

		int phoneNumberLength = recipientPhoneNumber.length();
		if (phoneNumberLength < 10 || 11 < phoneNumberLength) {
			throw new BaseException(MESSAGE_INVALID_PHONE_NUMBER);
		}

		RestTemplate restTemplate = new RestTemplate();

		// 6?????? ????????????
		String verificationNumber = makeRandomNumber();
		String verificationMessage = String.format("?????? ????????? %s ?????????.", verificationNumber);

		// Long time = System.currentTimeMillis();
		String time = Long.toString(System.currentTimeMillis());
		List<MessagesReqDto> messages = new ArrayList<>();
		// ????????? ???????????? ????????? ??????.
		messages.add(new MessagesReqDto(recipientPhoneNumber, verificationMessage)); // content????????? ?????????

		// ?????? json??? ?????? ???????????? ?????????.
		SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", "01087646410", "MangoLtd", messages);

		// ????????? ????????? json ????????? ??????????????????.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

		// ???????????? ?????? ??????????????? ????????????.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);

		// ?????? ????????? signature ????????????.
		String sig = makeSignature(time);
		System.out.println("sig -> " + sig);
		headers.set("x-ncp-apigw-signature-v2", sig);

		// ????????? ????????? jsonBody??? ????????? ????????????.
		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
		System.out.println(body.getBody());

		// restTemplate??? post ????????? ?????????. ??? ??? ????????? 202 ?????? ????????????.
		SendVerificationNumberResDto sendVerificationNumberResDto = restTemplate.postForObject(
			new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body,
			SendVerificationNumberResDto.class);
		// ????????? ????????? ?????? ????????? ???????????? ???????????????.
		Objects.requireNonNull(sendVerificationNumberResDto).setVerificationNumber(verificationNumber);

		return sendVerificationNumberResDto;
	}

	public SendSmsResDto sendSms(String recipientPhoneNumber, String content) throws
		JsonProcessingException,
		UnsupportedEncodingException,
		InvalidKeyException,
		NoSuchAlgorithmException,
		URISyntaxException, BaseException {

		int phoneNumberLength = recipientPhoneNumber.length();
		if (phoneNumberLength < 10 || 11 < phoneNumberLength) {
			throw new BaseException(MESSAGE_INVALID_PHONE_NUMBER);
		}

		if (content.isEmpty()) {
			throw new BaseException(MESSAGE_NO_ANY_CONTENT);
		}

		RestTemplate restTemplate = new RestTemplate();

		// Long time = System.currentTimeMillis();
		String time = Long.toString(System.currentTimeMillis());
		List<MessagesReqDto> messages = new ArrayList<>();
		// ????????? ???????????? ????????? ??????.
		messages.add(new MessagesReqDto(recipientPhoneNumber, content)); // content????????? ?????????

		// ?????? json??? ?????? ???????????? ?????????.
		SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", "01087646410", "MangoLtd", messages);

		// ????????? ????????? json ????????? ??????????????????.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

		// ???????????? ?????? ??????????????? ????????????.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);

		// ?????? ????????? signature ????????????.
		String sig = makeSignature(time);
		System.out.println("sig -> " + sig);
		headers.set("x-ncp-apigw-signature-v2", sig);

		// ????????? ????????? jsonBody??? ????????? ????????????.
		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
		System.out.println(body.getBody());

		// restTemplate??? post ????????? ?????????. ??? ??? ????????? 202 ?????? ????????????.
		SendSmsResDto sendSmsResDto = restTemplate.postForObject(
			new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body,
			SendSmsResDto.class);
		System.out.println(sendSmsResDto.getStatusCode());
		return sendSmsResDto;
	}

	@ResponseBody
	@PostMapping("/message")
	public BaseResponse<SendSmsResDto> smsService(@RequestBody MessagesReqDto messagesReqDto) throws
		UnsupportedEncodingException,
		NoSuchAlgorithmException,
		URISyntaxException,
		InvalidKeyException,
		JsonProcessingException {
		try {
			return new BaseResponse<>(sendSms(messagesReqDto.getTo(), messagesReqDto.getContent()));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@ResponseBody
	@PostMapping("/verification-number")
	public BaseResponse<SendVerificationNumberResDto> smsVerificationNumberService(@RequestBody
		VerificationReqDto verificationReqDto) throws UnsupportedEncodingException,
		NoSuchAlgorithmException,
		URISyntaxException,
		InvalidKeyException,
		JsonProcessingException {
		try {
			return new BaseResponse<>(sendVerificationNumber(verificationReqDto.getTo()));
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

}