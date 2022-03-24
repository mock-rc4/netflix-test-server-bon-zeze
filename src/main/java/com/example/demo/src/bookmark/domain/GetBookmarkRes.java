package com.example.demo.src.bookmark.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetBookmarkRes {
    private int videoIdx;
    private String photoUrl;
    private int ageGrade;
    private int season;
    //시즌 몇
    private int runningTime;
    //러닝타임
    private String character;
    //영화특징
}
