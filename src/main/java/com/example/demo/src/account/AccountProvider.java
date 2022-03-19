package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;

@Service

public class AccountProvider {

	private final AccountDao accountDao;
	private final JwtService jwtService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AccountProvider(AccountDao accountDao, JwtService jwtService) {
		this.accountDao = accountDao;
		this.jwtService = jwtService;
	}


	public int checkIsDuplicatedEmail(String email) throws BaseException {
		try {
			return accountDao.checkIsDuplicatedEmail(email);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public int checkIsDeactivatedAccount(String email) throws BaseException {
		try {
			return accountDao.checkIsDeactivatedAccount(email);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

}

