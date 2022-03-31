package com.example.demo.src.kakaoAccount;

import com.example.demo.src.kakaoAccount.domain.KakaoAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoOAuth {
    //OAuth (OAuth를 사용한 소셜 로그인 구현)
    //사용자가 애플리케이션에게 모든 권한을 넘기지 않고 사용자 대신 서비스를 이용할 수 있게 해주는 HTTP 기반의 보안 프로토콜

    public KakaoAccount getAccount(String code) {
        String accessToken = getAccessToken(code);
        KakaoAccount account = getAccountByToken(accessToken);
        return account;
    }

    public String getAccessToken(String code) {
        //Header 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //Body 만들기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "41826b90a5ce14c3d3c548fb2173482a"); //REST API 키
        params.add("redirect_url", "http://localhost:9000/kakao/login"); //카카오 로그인에서 사용할 OAuth Redirect URI - controller
        params.add("code", code);

        //요청하기 위해 Header+Body 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //POST 방식으로 요청하기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token", //요청할 서버 주소
                HttpMethod.POST,
                kakaoTokenRequest, //요청할때 보낼 데이터
                String.class
        );

        //Json-> 필요한 accessToken 만 반환
        String tokenJson = response.getBody();
        String accessToken = "";
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(tokenJson);
            accessToken = jsonObject.get("access_token").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("accessToken = " + accessToken);
        return accessToken;
    }

    private KakaoAccount getAccountByToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        String id = "";
        String email = "";
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject body = (JSONObject) jsonParser.parse(response.getBody());
            id = body.get("id").toString();
            JSONArray jsonArray = (JSONArray) body.get("kakao_account");
            email = (String) jsonArray.get(9); //email
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("id = " + id);
        System.out.println("email = " + email);
        return new KakaoAccount(id, email);
    }
}
