package com.example.demo.src.profilePhoto.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ProfilePhoto {
    private int profilePhotoIdx;
    private String profilePhotoUrl;
    private String category;
}
