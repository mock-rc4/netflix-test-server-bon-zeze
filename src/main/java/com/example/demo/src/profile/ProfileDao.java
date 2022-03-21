package com.example.demo.src.profile;

import com.example.demo.src.profile.domain.GetProfileReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(GetProfileReq getProfileReq) {
        String query = "insert into Profile (name, ageGrade, accountIdx, profilePhotoIdx) VALUES (?,?,?,?)";
        Object[] createParams = new Object[]{getProfileReq.getName(),
                getProfileReq.getAgeGrade(),
                getProfileReq.getAccountIdx(),
                getProfileReq.getProfilePhotoIdx()
        };
        this.jdbcTemplate.update(query, createParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
}
