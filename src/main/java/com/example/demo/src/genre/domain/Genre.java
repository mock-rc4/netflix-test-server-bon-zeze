package com.example.demo.src.genre.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {
    private int genreIdx;
    private String name;
}
