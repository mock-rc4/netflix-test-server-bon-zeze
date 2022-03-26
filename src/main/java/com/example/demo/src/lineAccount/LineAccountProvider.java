package com.example.demo.src.lineAccount;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.lineAccount.domain.LineAccount;
import com.example.demo.utils.JwtService;

@Service

public class LineAccountProvider {

	private final JwtService jwtService;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final LineAccountDao lineAccountDao;

	@Autowired
	public LineAccountProvider(LineAccountDao lineAccountDao, JwtService jwtService) {
		this.lineAccountDao = lineAccountDao;
		this.jwtService = jwtService;
	}

	public int checkLineAccountByEmail(String email) throws BaseException {
		try {
			return lineAccountDao.checkLineAccountByEmail(email);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public LineAccount.GetLineAccountResDto getLineAccountById(String id) throws BaseException {
		try {
			return lineAccountDao.getLineAccountById(id);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}
}