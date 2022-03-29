package com.example.demo.src.bookmark.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkReq {
    private int profileIdx;
    private int videoIdx;
}
