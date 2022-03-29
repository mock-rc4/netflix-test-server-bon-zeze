package com.example.demo.src.category.domain;

import com.example.demo.src.genre.domain.Genre;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategory {

    public static final String TOP_TEN = "한국의 Top 10 콘텐츠";
    public static final String BOOKMARK = "내가 찜한 콘텐츠";
    public static final String POPULAR = "지금 뜨는 콘텐츠";
    public static final String WATCHING = "시청중인 콘텐츠";

    private List<String> main_categories;
    private List<Genre> genres_categories;

    public MainCategory(List<Genre> genres) {
        this.main_categories = new ArrayList<>(Arrays.asList(TOP_TEN, BOOKMARK, POPULAR, WATCHING));
        this.genres_categories = genres;
    }

}
