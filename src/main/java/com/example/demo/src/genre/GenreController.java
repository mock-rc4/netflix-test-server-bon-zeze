package com.example.demo.src.genre;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.genre.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private final GenreProvider genreProvider;

    @GetMapping("")
    public BaseResponse<List<Genre>> getGenres() {
        try {
            List<Genre> genres = genreProvider.getGenres();
            return new BaseResponse<>(genres);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
