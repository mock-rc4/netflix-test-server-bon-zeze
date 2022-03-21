package com.example.demo.src.profile.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Profile {
    private int profileIdx;
    private int status;
    private String name;
    private String language;
    private int ageGrade;
    private int accountIdx;
    private int profilePhotoIdx;

}
