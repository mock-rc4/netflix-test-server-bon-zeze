package com.example.demo.src.search.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Search {
    private int searchIdx;
    private String keyword;
}
