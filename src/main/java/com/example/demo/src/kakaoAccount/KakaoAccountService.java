package com.example.demo.src.kakaoAccount;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.AccountDao;
import com.example.demo.src.kakaoAccount.domain.KakaoAccount;
import com.example.demo.src.kakaoAccount.domain.PostKakaoAccount;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class KakaoAccountService {

    private final KakaoOAuth kakaoOAuth;
    private final KakaoAccountDao kakaoDao;
    private final AccountDao accountDao;
    private final JwtService jwtService;

    @Autowired
    public KakaoAccountService(KakaoOAuth kakaoOAuth, KakaoAccountDao kakaoDao, AccountDao accountDao, JwtService jwtService) {
        this.kakaoOAuth = kakaoOAuth;
        this.kakaoDao = kakaoDao;
        this.accountDao = accountDao;
        this.jwtService = jwtService;
    }

    public PostKakaoAccount kakaoLogin(String code) throws BaseException {
        KakaoAccount kakaoAccount = kakaoOAuth.getAccount(code);
        String kakaoId = kakaoAccount.getKakaoId();
        String email = kakaoAccount.getEmail();

        if (accountDao.checkExistsEmail(email) == 0) {
            throw new BaseException(INVALID_USER_MAIL_LOGIN);
        }

        //PostKakaoAccount postKakaoAccount = kakaoDao.getAccountBySocialId(kakaoId, email); //계정 등록이 필요
        int accountIdx = kakaoDao.getAccountByEmail(email);
        String jwt = jwtService.createJwt(accountIdx);
        return new PostKakaoAccount(accountIdx, jwt);
    }
}
