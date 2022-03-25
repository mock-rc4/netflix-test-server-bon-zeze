package com.example.demo.src.character;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public List<String> getVideoCharacters(int videoIdx) {
        String query = "select C.name from `Character` C\n"
                + "join CharacterContact CC on CC.characterIdx = C.characterIdx\n"
                + "where CC.videoIdx = ?";
        return this.jdbcTemplate.query(query, charactersRowMapper(), videoIdx);
    }

    private RowMapper<String> charactersRowMapper() {
        return (rs, rowNum) -> new String(rs.getString("name"));
    }
}
