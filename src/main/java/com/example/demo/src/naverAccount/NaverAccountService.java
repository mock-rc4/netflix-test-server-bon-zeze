package com.example.demo.src.naverAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.naverAccount.domain.NaverAccount;
import com.example.demo.utils.JwtService;

@Service
public class NaverAccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final JwtService jwtService;
	private final NaverAccountDao naverAccountDao;
	private final NaverAccountProvider naverAccountProvider;

	@Autowired
	public NaverAccountService(JwtService jwtService, NaverAccountDao naverAccountDao,
		NaverAccountProvider naverAccountProvider) {
		this.jwtService = jwtService;
		this.naverAccountProvider = naverAccountProvider;
		this.naverAccountDao = naverAccountDao;
	}

	public NaverAccount.PostSignInNaverAccountResDto createNaverAccount(
		NaverAccount.PostSignInNaverAccountReqDto requestDto) throws BaseException {
		if (naverAccountProvider.checkNaverAccountByEmail(requestDto.getEmail()) == 1) {
			throw new BaseException(SOCIAL_EXISTS_ACCOUNT);
		}
		try {
			int accountIdx = naverAccountDao.createNaverAccount(requestDto);
			String jwt = jwtService.createJwt(accountIdx);
			return new NaverAccount.PostSignInNaverAccountResDto(accountIdx, jwt);

		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}