package com.example.demo.src.kakaoAccount.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostKakaoAccount {
    private int accountIdx;
    private String jwt;
}
