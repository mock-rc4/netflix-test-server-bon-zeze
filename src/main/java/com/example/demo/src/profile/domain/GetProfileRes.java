package com.example.demo.src.profile.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class GetProfileRes {
    private int profileIdx;
    private String jwt;
}
