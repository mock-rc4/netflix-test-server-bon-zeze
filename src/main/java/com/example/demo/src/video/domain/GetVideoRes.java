package com.example.demo.src.video.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetVideoRes {
    private int videoIdx;
    private String photoUrl;
    private int ageGrade;

    //영화냐 시리즈냐에 따라 보여주는게 다르기는 함.(리팩토링 필요)
    private int season;
    private String runningTime;
    private String resolution;
    private List<String> character;

    public GetVideoRes(int videoIdx, String photoUrl, int ageGrade, int season, String runningTime, String resolution) {
        this.videoIdx = videoIdx;
        this.photoUrl = photoUrl;
        this.ageGrade = ageGrade;
        this.season = season;
        this.runningTime = runningTime;
        this.resolution = resolution;
    }
}
