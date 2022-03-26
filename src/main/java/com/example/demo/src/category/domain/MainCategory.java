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

    public static final String TOP_TEN = "topten";
    public static final String BOOKMARK = "bookmark";
    public static final String POPULAR = "popular";
    public static final String WATCHING = "watching";

    private List<String> main_categories_uri;
    private List<Genre> genres_categories_uri;

    public MainCategory(List<Genre> genres) {
        this.main_categories_uri = new ArrayList<>(Arrays.asList(TOP_TEN, BOOKMARK, POPULAR, WATCHING));
        this.genres_categories_uri = genres;
    }

}
