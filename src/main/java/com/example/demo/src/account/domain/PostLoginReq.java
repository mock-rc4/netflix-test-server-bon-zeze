package com.example.demo.src.account.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLoginReq {
    private String emailOrPhone;
    private String password;
}
