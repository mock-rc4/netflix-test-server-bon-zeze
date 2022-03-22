package com.example.demo.src.profile.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileRes {
    private int profileIdx;
    private String jwt;
}
