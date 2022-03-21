package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import com.example.demo.config.secret.Secret;
import com.example.demo.src.account.domain.PostAccountRes;
import com.example.demo.src.account.domain.PostLoginReq;
import com.example.demo.utils.AES128;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.domain.Account;
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
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }
	public String checkHasMembership(String email) throws BaseException {
		try {
			return accountDao.checkHasMembership(email);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}



	public int checkIsDeactivatedAccount(String email) throws BaseException {
        try {
            return accountDao.checkIsDeactivatedAccount(email);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

	public int checkIsValidAccountIdx(int accountIdx) throws BaseException {
		try {
			return accountDao.checkIsValidAccountIdx(accountIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

    public List<Account.getAccountsDto> getAccounts() throws BaseException {
        try {
            List<Account.getAccountsDto> getAccountsDto = accountDao.getAccounts();
            return getAccountsDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Account.getAccountsDto> getAccountsByMembership(String membership) throws BaseException {
        try {
            List<Account.getAccountsDto> getAccountsDto = accountDao.getAccountsByMembership(membership);
            return getAccountsDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Account.getResDto getAccount(int accountIdx) throws BaseException {
        try {
            Account.getResDto getResDto = accountDao.getAccount(accountIdx);
            return getResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostAccountRes login(PostLoginReq postLoginReq) throws BaseException {
        Account account = accountDao.getPassword(postLoginReq);
        String password;
        //복호화
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(account.getPassword()); // 암호화
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (postLoginReq.getPassword().equals(password)) {
            int memberIdx = accountDao.getPassword(postLoginReq).getAccountIdx();

            //jwt 발급(회원 일치)
            String jwt = jwtService.createJwt(memberIdx);
            return new PostAccountRes(memberIdx, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    public PostAccountRes logout(int accountIdx) throws BaseException {
        try {
            int accountIdxByJwt = jwtService.getUserIdx();

            //jwt 토큰 만료
            String jwt = jwtService.removeJwt(accountIdxByJwt);
            return new PostAccountRes(accountIdxByJwt, jwt);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOGOUT);
        }
    }
}

