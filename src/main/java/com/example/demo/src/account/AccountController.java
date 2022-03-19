package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.account.domain.Account;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/sign-up")
	public BaseResponse<Account.createResDto> createAccount(@RequestBody Account.createReqDto requestDto) {
		try {
			String email = requestDto.getEmail();
			if (!isRegexEmail(email)) {
				return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
			}
			if (!isRegexPassword(requestDto.getPassword())) {
				return new  BaseResponse<>(POST_ACCOUNTS_INVALID_PASSWORD);
			}
			if (!isRegexPhoneNumber(requestDto.getPhoneNumber())) {
				return new  BaseResponse<>(POST_ACCOUNTS_INVALID_PHONE_NUMBER);
			}
			if (requestDto.getMembership() == null) {
				return new BaseResponse<>(POST_ACCOUNTS_EMPTY_MEMBERSHIP);
			}
			Account.createResDto createResDto = accountService.createAccount(requestDto);
			return new BaseResponse<>(createResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}