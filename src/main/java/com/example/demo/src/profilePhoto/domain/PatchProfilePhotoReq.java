package com.example.demo.src.profilePhoto.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchProfilePhotoReq {
    int profileIdx;
    int profilePhotoIdx;
}
