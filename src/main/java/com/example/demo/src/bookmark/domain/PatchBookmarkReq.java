package com.example.demo.src.bookmark.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchBookmarkReq {
    private int profileIdx;
    private int videoIdx;
    private int newStatus;
}
