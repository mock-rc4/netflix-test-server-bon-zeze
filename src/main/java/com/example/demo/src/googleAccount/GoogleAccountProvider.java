package com.example.demo.src.googleAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.googleAccount.domain.GoogleAccount;
import com.example.demo.utils.JwtService;

@Service
public class GoogleAccountProvider {

	private final JwtService jwtService;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final GoogleAccountDao googleAccountDao;

	@Autowired
	public GoogleAccountProvider(GoogleAccountDao googleAccountDao, JwtService jwtService) {
		this.googleAccountDao = googleAccountDao;
		this.jwtService = jwtService;
	}

	public int checkGoogleAccountByEmail(String email) throws BaseException {
		try {
			return googleAccountDao.checkGoogleAccountByEmail(email);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public GoogleAccount.GetGoogleAccountResDto getGoogleAccountById(String id) throws BaseException {
		try {
			return googleAccountDao.getGoogleAccountById(id);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
