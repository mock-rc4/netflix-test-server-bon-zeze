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
			throw new BaseException(SMS_INVALID_PHONE_NUMBER);
		}

		RestTemplate restTemplate = new RestTemplate();

		// 6글자 인증번호
		String verificationNumber = makeRandomNumber();
		String verificationMessage = String.format("인증 번호는 %s 입니다.", verificationNumber);

		// Long time = System.currentTimeMillis();
		String time = Long.toString(System.currentTimeMillis());
		List<MessagesReqDto> messages = new ArrayList<>();
		// 보내는 사람에게 내용을 보냄.
		messages.add(new MessagesReqDto(recipientPhoneNumber, verificationMessage)); // content부분이 내용임

		// 전체 json에 대해 메시지를 만든다.
		SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", "01087646410", "MangoLtd", messages);

		// 쌓아온 바디를 json 형태로 변환시켜준다.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

		// 헤더에서 여러 설정값들을 잡아준다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);

		// 제일 중요한 signature 서명하기.
		String sig = makeSignature(time);
		System.out.println("sig -> " + sig);
		headers.set("x-ncp-apigw-signature-v2", sig);

		// 위에서 조립한 jsonBody와 헤더를 조립한다.
		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
		System.out.println(body.getBody());

		// restTemplate로 post 요청을 보낸다. 별 일 없으면 202 코드 반환된다.
		SendVerificationNumberResDto sendVerificationNumberResDto = restTemplate.postForObject(
			new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body,
			SendVerificationNumberResDto.class);
		// 반환된 결과에 확인 번호를 추가하여 맵핑시킨다.
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
			throw new BaseException(SMS_INVALID_PHONE_NUMBER);
		}

		if (content.isEmpty()) {
			throw new BaseException(SMS_NO_ANY_CONTENT);
		}

		RestTemplate restTemplate = new RestTemplate();

		// Long time = System.currentTimeMillis();
		String time = Long.toString(System.currentTimeMillis());
		List<MessagesReqDto> messages = new ArrayList<>();
		// 보내는 사람에게 내용을 보냄.
		messages.add(new MessagesReqDto(recipientPhoneNumber, content)); // content부분이 내용임

		// 전체 json에 대해 메시지를 만든다.
		SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", "01087646410", "MangoLtd", messages);

		// 쌓아온 바디를 json 형태로 변환시켜준다.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

		// 헤더에서 여러 설정값들을 잡아준다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);

		// 제일 중요한 signature 서명하기.
		String sig = makeSignature(time);
		System.out.println("sig -> " + sig);
		headers.set("x-ncp-apigw-signature-v2", sig);

		// 위에서 조립한 jsonBody와 헤더를 조립한다.
		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
		System.out.println(body.getBody());

		// restTemplate로 post 요청을 보낸다. 별 일 없으면 202 코드 반환된다.
		SendSmsResDto sendSmsResDto = restTemplate.postForObject(
			new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body,
			SendSmsResDto.class);
		System.out.println(sendSmsResDto.getStatusCode());
		return sendSmsResDto;
	}

	@ResponseBody
	@PostMapping("/send-message")
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
	@PostMapping("/send-verification-number")
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