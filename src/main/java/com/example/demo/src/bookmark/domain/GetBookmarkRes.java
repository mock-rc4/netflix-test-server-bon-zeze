package com.example.demo.src.bookmark.domain;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetBookmarkRes {
    private int bookmarkIdx;
    private int videoIdx;
    private String photoUrl;
    private int ageGrade;

    //영화냐 시리즈냐에 따라 보여주는게 다르기는 함.(리팩토링 필요)
    private int season;
    private int runningTime;
    private String resolution;
    private List<String> character;

    public GetBookmarkRes(int bookmarkIdx, int videoIdx, String photoUrl, int ageGrade, int season, int runningTime, String resolution) {
        this.bookmarkIdx = bookmarkIdx;
        this.videoIdx = videoIdx;
        this.photoUrl = photoUrl;
        this.ageGrade = ageGrade;
        this.season = season;
        this.runningTime = runningTime;
        this.resolution = resolution;
    }
}
