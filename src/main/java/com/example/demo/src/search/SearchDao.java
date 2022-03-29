package com.example.demo.src.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveSearchKeyword(String keyword) {
        String query = "insert into Search (keyword) VALUES (?)";
        return this.jdbcTemplate.update(query, keyword);
    }
	public String getTheMostSearchedWord() {
		String theMostSearchedWordQuery = "select keyword from Search group by keyword order by count(keyword) desc limit 1";
		return this.jdbcTemplate.queryForObject(theMostSearchedWordQuery,
			String.class);
	}
}
