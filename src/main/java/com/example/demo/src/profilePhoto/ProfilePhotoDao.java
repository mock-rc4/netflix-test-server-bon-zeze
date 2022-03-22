package com.example.demo.src.profilePhoto;

import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfilePhotoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProfilePhoto getProfilePhoto(int profilePhotoIdx) {
        String query = "select * from ProfilePhoto where profilePhotoIdx = ?";
        int param = profilePhotoIdx;

        return jdbcTemplate.queryForObject(query, profilePhotoRowMapper(), param);
    }

    public List<ProfilePhoto> getProfilePhotos() {
        String query = "select * from ProfilePhoto order by category";

        return jdbcTemplate.query(query, profilePhotoRowMapper());
    }

    public int getProfilePhotoCount() {
        String lastInsertIdQuery = "select count(*) from ProfilePhoto";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    private RowMapper<ProfilePhoto> profilePhotoRowMapper() {
        return (rs, rowNum) -> new ProfilePhoto(
                rs.getInt("profilePhotoIdx"),
                rs.getString("profilePhotoUrl"),
                rs.getString("category"));
    }

}
