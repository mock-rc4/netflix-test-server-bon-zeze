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

public class NaverAccountProvider {

	private final JwtService jwtService;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final NaverAccountDao naverAccountDao;

	@Autowired
	public NaverAccountProvider(NaverAccountDao naverAccountDao, JwtService jwtService) {
		this.naverAccountDao = naverAccountDao;
		this.jwtService = jwtService;
	}

	public int checkNaverAccountByEmail(String email) throws BaseException {
		try {
			return naverAccountDao.checkNaverAccountByEmail(email);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public NaverAccount.GetNaverAccountResDto getNaverAccountById(String id) throws BaseException {
		try {
			return naverAccountDao.getNaverAccountById(id);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}