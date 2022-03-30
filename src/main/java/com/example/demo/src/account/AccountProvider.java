package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccountProvider(AccountDao accountDao, JwtService jwtService) {
        this.accountDao = accountDao;
        this.jwtService = jwtService;
    }


    public int checkIsDuplicatedEmail(String email) throws BaseException {
        try {
            return accountDao.checkExistsEmail(email);
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
			logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostAccountRes login(PostLoginReq postLoginReq) throws BaseException {
        String id = checkExistAccount(postLoginReq);

        if (id.equals(EMAIL)) {
            return loginByEmail(postLoginReq);
        } else {
            return loginByPhone(postLoginReq);
        }
    }

    //리팩토링 진행중.
    public PostAccountRes logout(int accountIdx) throws BaseException {
        try {
            int accountIdxByJwt = jwtService.getUserIdx();
            String membership = accountDao.getMembership(accountIdx);

            //jwt 토큰 만료
            String jwt = jwtService.removeJwt(accountIdxByJwt);
            return new PostAccountRes(accountIdxByJwt, membership, jwt);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOGOUT);
        }
    }

    private String checkExistAccount(PostLoginReq postLoginReq) throws BaseException {
        String emailOrPhone = postLoginReq.getEmailOrPhone();
        String id = checkIsEmail(emailOrPhone);

        if (id == EMAIL) {
            if (accountDao.checkExistsEmail(emailOrPhone) == 0) {
                throw new BaseException(INVALID_USER_MAIL_LOGIN);
            }
            return EMAIL;
        } else {
            if (accountDao.checkExistsPhoneNumber(emailOrPhone) == 0) {
                throw new BaseException(INVALID_USER_PHONE_LOGIN);
            }
            return PHONE;
        }
    }

    private String checkIsEmail(String emailOrPhone) {
        for (int i = 0; i < emailOrPhone.length(); i++) {
            if ('a' <= emailOrPhone.charAt(i) && emailOrPhone.charAt(i) <= 'z') {
                return EMAIL;
            }
        }
        return PHONE;
    }

    private PostAccountRes loginByEmail(PostLoginReq postLoginReq) throws BaseException {
        Account account = accountDao.getPasswordByEmail(postLoginReq);
        String password = decryptPassword(account);
        String membership = getAccountMembership(account);

        if (postLoginReq.getPassword().equals(password)) {
            int accountIdx = accountDao.getPasswordByEmail(postLoginReq).getAccountIdx();
            //jwt 발급(회원 일치)
            String jwt = jwtService.createJwt(accountIdx);
            return new PostAccountRes(accountIdx, membership, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    private PostAccountRes loginByPhone(PostLoginReq postLoginReq) throws BaseException {
        Account account = accountDao.getPasswordByPhone(postLoginReq);
        String password = decryptPassword(account);
        String membership = getAccountMembership(account);

        if (postLoginReq.getPassword().equals(password)) {
            int accountIdx = accountDao.getPasswordByPhone(postLoginReq).getAccountIdx();
            String jwt = jwtService.createJwt(accountIdx);
            return new PostAccountRes(accountIdx, membership, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    private String getAccountMembership(Account account) throws BaseException {
        try {
            return accountDao.getMembership(account.getAccountIdx());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private String decryptPassword(Account account) throws BaseException {
        try {
            return new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(account.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
    }
}

