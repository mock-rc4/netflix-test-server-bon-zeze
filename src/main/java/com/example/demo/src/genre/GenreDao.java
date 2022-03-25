package com.example.demo.src.genre;

import com.example.demo.src.genre.domain.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> getGenres() {
        String query = "select genreIdx, name from Genre";
        return this.jdbcTemplate.query(query, genreRowMapper());
    }

    private RowMapper<Genre> genreRowMapper() {
        return (rs, rowNum) -> new Genre(
                rs.getInt("genreIdx"),
                rs.getString("name"));
    }
}
