package com.example.demo.src.search;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;

@Service
public class SearchProvider {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SearchDao searchDao;

	@Autowired
	public SearchProvider(SearchDao searchDao) {
		this.searchDao = searchDao;
	}

	public String getTheMostSearchedWord() throws BaseException {
		try {
			return searchDao.getTheMostSearchedWord();
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}