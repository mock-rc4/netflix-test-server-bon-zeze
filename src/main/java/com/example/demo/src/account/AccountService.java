package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexPassword;

import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.account.domain.PatchAccountReq;
import com.example.demo.src.account.domain.PatchPasswordReq;
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

    public Account.createResDto createAccount(Account.createReqDto requestDto) throws BaseException {
        // 이메일이 특정 계정 정보로서 존재하는 중복된 이메일인지 확인
        if (accountProvider.checkIsDuplicatedEmail(requestDto.getEmail()) == 1) {
            throw new BaseException(POST_ACCOUNTS_EXISTS_EMAIL);
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
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원탈퇴
    public void deactivateAccount(Account.DeactivateReqDto deactivateReqDto) throws BaseException {
        int result = 0;
        try {
            result = accountDao.deactivateAccount(deactivateReqDto); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
        if (result == 0) {
            throw new BaseException(MODIFY_FAIL_DEACTIVATE_ACCOUNT);
        }
    }

    public void updatePassword(PatchPasswordReq patchPasswordReq) throws BaseException {
        Account account = accountDao.getPasswordByAccountIdx(patchPasswordReq);

        //기존 비밀번호 일치 확인
        String password = decryptPassword(account);
        if (!password.equals(patchPasswordReq.getPassword())) {
            throw new BaseException(WRONG_PASSWORD);
        }

        //기존 비밀번호가 맞고, 새 비밀번호 형식이 맞으면 update
        String newPassword = patchPasswordReq.getNewPassword();
        newPassword = encryptPassword(newPassword);
        patchPasswordReq.setNewPassword(newPassword);

        try {
            int result = accountDao.updatePassword(patchPasswordReq);
            if (result == 0) {
                throw new BaseException(PATCH_ACCOUNTS_PASSWORD_UPDATE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateEmail(PatchAccountReq patchAccountReq) throws BaseException {
        if (accountProvider.checkIsDuplicatedEmail(patchAccountReq.getUpdateParam()) == 1) {
            throw new BaseException(POST_ACCOUNTS_EXISTS_EMAIL);
        }

        try {
            int result = accountDao.updateEmail(patchAccountReq);
            if (result == 0) {
                throw new BaseException(PATCH_ACCOUNTS_EMAIL_UPDATE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updatePhoneNumber(PatchAccountReq patchAccountReq) throws BaseException {
        try {
            int result = accountDao.updatePhoneNumber(patchAccountReq);
            if (result == 0) {
                throw new BaseException(PATCH_ACCOUNTS_PHONE_UPDATE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateMemberShip(PatchAccountReq patchAccountReq) throws BaseException {
        try {
            int result = accountDao.updateMemberShip(patchAccountReq);
            if (result == 0) {
                throw new BaseException(PATCH_ACCOUNTS_MEMBERSHIP_UPDATE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private String encryptPassword(String password) throws BaseException {
        try {
            return new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
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