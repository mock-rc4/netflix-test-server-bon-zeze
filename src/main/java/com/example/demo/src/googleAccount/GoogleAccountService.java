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
public class GoogleAccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final JwtService jwtService;
	private final GoogleAccountDao googleAccountDao;
	private final GoogleAccountProvider googleAccountProvider;

	@Autowired
	public GoogleAccountService(JwtService jwtService, GoogleAccountDao googleAccountDao,
		GoogleAccountProvider googleAccountProvider) {
		this.jwtService = jwtService;
		this.googleAccountProvider = googleAccountProvider;
		this.googleAccountDao = googleAccountDao;
	}

	public GoogleAccount.PostSignInGoogleAccountResDto createGoogleAccount(
		GoogleAccount.PostSignInGoogleAccountReqDto requestDto) throws BaseException {
		if (googleAccountProvider.checkGoogleAccountByEmail(requestDto.getEmail()) == 1) {
			throw new BaseException(SOCIAL_EXISTS_ACCOUNT);
		}
		try {
			int accountIdx = googleAccountDao.createGoogleAccount(requestDto);
			String jwt = jwtService.createJwt(accountIdx);
			return new GoogleAccount.PostSignInGoogleAccountResDto(accountIdx, jwt);

		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
