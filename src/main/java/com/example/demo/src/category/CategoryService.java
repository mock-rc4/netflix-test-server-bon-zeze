package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.domain.MainCategory;
import com.example.demo.src.genre.GenreDao;
import com.example.demo.src.genre.GenreProvider;
import com.example.demo.src.genre.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CategoryService {

    private final GenreProvider genreProvider;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CategoryService(GenreProvider genreProvider) {
        this.genreProvider = genreProvider;
    }

    public MainCategory getCategories() throws BaseException {
        try {
            return new MainCategory(genreProvider.getGenres());
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(RESPONSE_CATEGORY_ERROR);
        }
    }
}
