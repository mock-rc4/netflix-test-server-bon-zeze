package com.example.demo.src.bookmark.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    private int profileIdx;
    private int videoIdx;
    private int status;
}
