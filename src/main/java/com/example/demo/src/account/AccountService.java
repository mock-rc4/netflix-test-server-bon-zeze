package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.account.domain.Account;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;

@Service
public class AccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final AccountDao accountDao;
	private final AccountProvider accountProvider;
	private final JwtService jwtService;

	@Autowired
	public AccountService(AccountDao accountDao, AccountProvider accountProvider, JwtService jwtService) {
		this.accountDao = accountDao;
		this.accountProvider = accountProvider;
		this.jwtService = jwtService;
	}

	public Account.createResDto createAccount(Account.createReqDto  requestDto) throws BaseException {
		// 이메일이 특정 계정 정보로서 존재하는 중복된 이메일인지 확인
		if (accountProvider.checkIsDuplicatedEmail(requestDto.getEmail()) == 1) {
		    throw new BaseException(POST_ACCOUNTS_EXISTS_EMAIL);
		}
		// 비활성화된 계정인지 확인
		if (accountProvider.checkIsDeactivatedAccount(requestDto.getEmail()) == 0) {
			throw new BaseException(POST_ACCOUNTS_DEACTIVATED_ACCOUNT);
		}

		String pwd;
		try {
			pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(requestDto.getPassword());
			requestDto.setPassword(pwd);
		} catch (Exception ignored) {
			throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
		}

		try {
			int accountIdx = accountDao.createAccount(requestDto);


			String jwt = jwtService.createJwt(accountIdx);
			return new Account.createResDto(accountIdx, jwt);

		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}