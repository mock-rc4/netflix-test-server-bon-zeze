package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class SearchService {

    private final SearchDao searchDao;

    @Autowired
    public SearchService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    public void saveSearch(String keyword) throws BaseException {
        try {
            searchDao.saveSearchKeyword(keyword);
        } catch (Exception exception) {
            throw new BaseException(SAVE_SEARCH_ERROR);
        }
    }
}
