package com.example.demo.src.kakaoAccount;

import com.example.demo.config.BaseException;
import com.example.demo.src.kakaoAccount.domain.PostKakaoAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kakao")
public class KakaoAccountController {

    @Autowired
    private KakaoAccountService kakaoService;
    private static final String REST_API_KEY = "41826b90a5ce14c3d3c548fb2173482a";
    private static final String REDIRECT_URI = "http://localhost:9000/kakao/login";

    @GetMapping("/authorize")
    public String authorize() {
        String url = "'https://kauth.kakao.com/oauth/authorize?" +
                "client_id=" +REST_API_KEY+
                "&redirect_uri=" + REDIRECT_URI+
                "&response_type=code'";
        return url;
    }

    @GetMapping("/login")
    public PostKakaoAccount login(String code) throws BaseException {
        PostKakaoAccount account = kakaoService.kakaoLogin(code);
        return account;
    }
}
