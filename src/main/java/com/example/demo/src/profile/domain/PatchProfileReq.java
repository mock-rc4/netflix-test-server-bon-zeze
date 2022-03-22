package com.example.demo.src.profile.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchProfileReq {
    //관람등급 설정 API 는 따로
    private int profileIdx;
    private String name;
    private String language;
    private int settingAutoNextPlay;
    private int settingAutoPrePlay;
}
