package com.example.demo.src.category.domain;

import com.example.demo.src.genre.domain.Genre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainCategory {

    private static final String TOP_TEN = "topten";
    private static final String BOOKMARK = "bookmark";
    private static final String POPULAR = "popular";
    private static final String WATCHING = "watching";

    private String topten_uri;
    private String bookmark_uri;
    private String popular_uri;
    private String watching_uri;
    private List<Genre> genres_uri;


    public MainCategory(List<Genre> GENRES) {
        this(TOP_TEN, BOOKMARK, POPULAR, WATCHING, GENRES);
    }

    public MainCategory(String topten_uri, String bookmark_uri, String popular_uri, String watching_uri, List<Genre> genres_uri) {
        this.topten_uri = topten_uri;
        this.bookmark_uri = bookmark_uri;
        this.popular_uri = popular_uri;
        this.watching_uri = watching_uri;
        this.genres_uri = genres_uri;
    }
}
