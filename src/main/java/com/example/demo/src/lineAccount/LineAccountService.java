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
public class LineAccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final JwtService jwtService;
	private final LineAccountDao lineAccountDao;
	private final LineAccountProvider lineAccountProvider;

	@Autowired
	public LineAccountService(JwtService jwtService, LineAccountDao lineAccountDao,
		LineAccountProvider lineAccountProvider) {
		this.jwtService = jwtService;
		this.lineAccountProvider = lineAccountProvider;
		this.lineAccountDao = lineAccountDao;
	}

	public LineAccount.PostSignInLineAccountResDto createLineAccount(
		LineAccount.PostSignInLineAccountReqDto requestDto) throws BaseException {
		if (lineAccountProvider.checkLineAccountByEmail(requestDto.getEmail()) == 1) {
			throw new BaseException(SOCIAL_EXISTS_ACCOUNT);
		}
		try {
			int accountIdx = lineAccountDao.createLineAccount(requestDto);
			String jwt = jwtService.createJwt(accountIdx);
			return new LineAccount.PostSignInLineAccountResDto(accountIdx, jwt);

		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}
