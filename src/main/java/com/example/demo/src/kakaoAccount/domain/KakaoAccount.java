package com.example.demo.src.kakaoAccount.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccount {
    private String kakaoId;
    private String email;
}
