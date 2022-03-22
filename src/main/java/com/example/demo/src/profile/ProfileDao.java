package com.example.demo.src.profile;

import com.example.demo.src.profile.domain.PatchProfileReq;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profilePhoto.ProfilePhotoDao;
import com.example.demo.src.profilePhoto.domain.PatchProfilePhotoReq;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDao {

    private final JdbcTemplate jdbcTemplate;
    private final ProfilePhotoDao profilePhotoDao;

    @Autowired
    public ProfileDao(JdbcTemplate jdbcTemplate, ProfilePhotoDao profilePhotoDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.profilePhotoDao = profilePhotoDao;
    }

    public int create(PostProfileReq postProfileReq) {
        String query = "insert into Profile (name, ageGrade, accountIdx, profilePhotoIdx) VALUES (?,?,?,?)";
        Object[] createParams = new Object[]{postProfileReq.getName(),
                postProfileReq.getAgeGrade(),
                postProfileReq.getAccountIdx(),
                postProfileReq.getProfilePhotoIdx()
        };
        this.jdbcTemplate.update(query, createParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public ProfilePhoto updateProfilePhoto(PatchProfilePhotoReq patchProfilePhotoReq) {
        String query = "update Profile set updatedAt = now(), profilePhotoIdx = ? where profileIdx = ?";
        int profileIdx = patchProfilePhotoReq.getProfileIdx();
        int profilePhotoIdx = patchProfilePhotoReq.getProfilePhotoIdx();
        this.jdbcTemplate.update(query, profilePhotoIdx, profileIdx);

        return profilePhotoDao.getProfilePhoto(profilePhotoIdx);
    }

    public int updateProfile(PatchProfileReq patchProfileReq) {
        String query = "update Profile set updatedAt = now(), name = ?, language = ?, settingAutoNextPlay = ?, settingAutoPrePlay =?\n"
                + "where  profileIdx = ?";
        Object[] updateParam = new Object[]{
                patchProfileReq.getName(),
                patchProfileReq.getLanguage(),
                patchProfileReq.getSettingAutoNextPlay(),
                patchProfileReq.getSettingAutoPrePlay(),
                patchProfileReq.getProfileIdx()
        };
        return this.jdbcTemplate.update(query, updateParam);
    }
}
