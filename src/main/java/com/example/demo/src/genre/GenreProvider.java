package com.example.demo.src.genre;

import com.example.demo.config.BaseException;
import com.example.demo.src.genre.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class GenreProvider {

    private final GenreDao genreDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GenreProvider(GenreDao genreDao) {
        this.genreDao = genreDao;
    }


    public List<Genre> getGenres() throws BaseException {
        try {
            return genreDao.getGenres();
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
