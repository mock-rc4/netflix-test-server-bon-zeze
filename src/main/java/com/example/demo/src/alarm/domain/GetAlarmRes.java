package com.example.demo.src.alarm.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAlarmRes {
    private int alarmIdx;
    private int videoIdx;
    private String title;
    private String photoUrl;
    private LocalDateTime openDate;
}
