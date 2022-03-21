package com.example.demo.src.profile.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProfileReq {
    private String name;
    //기본 15세. res 요청 = 0~12 (12세 이하등급)
    private int ageGrade = 15;
    private int accountIdx;
    private int profilePhotoIdx;
}
