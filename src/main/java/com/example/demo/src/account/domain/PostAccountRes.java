package com.example.demo.src.account.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAccountRes {
    private int accountIdx;
    private String jwt;
}
