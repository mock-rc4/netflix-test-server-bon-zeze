package com.example.demo.src.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.profile.domain.PatchProfileReq;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.Profile;
import com.example.demo.src.profilePhoto.ProfilePhotoDao;
import com.example.demo.src.profilePhoto.domain.PatchProfilePhotoReq;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;

@Repository
public class ProfileDao {

	private static final int ADULT_AGE_GRADE = 18;
	private final JdbcTemplate jdbcTemplate;
	private final ProfilePhotoDao profilePhotoDao;

	@Autowired
	public ProfileDao(JdbcTemplate jdbcTemplate, ProfilePhotoDao profilePhotoDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.profilePhotoDao = profilePhotoDao;
	}

	public int create(PostProfileReq postProfileReq) {
		String query = "insert into Profile (name, ageGrade, accountIdx, profilePhotoIdx) VALUES (?,?,?,?)";
		Object[] createParams = new Object[] {postProfileReq.getName(),
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
		String query =
			"update Profile set updatedAt = now(), name = ?, language = ?, settingAutoNextPlay = ?, settingAutoPrePlay =?\n"
				+ "where  profileIdx = ?";
		Object[] updateParam = new Object[] {
			patchProfileReq.getName(),
			patchProfileReq.getLanguage(),
			patchProfileReq.getSettingAutoNextPlay(),
			patchProfileReq.getSettingAutoPrePlay(),
			patchProfileReq.getProfileIdx()
		};
		return this.jdbcTemplate.update(query, updateParam);
	}

	// 프로필 비활성화
	public int deactivate(Profile.DeactivateReqDto deactivateReq) {
		String query = "update Profile set status = ?, updatedAt = NOW() where profileIdx = ?";
		Object[] params = new Object[] {0, deactivateReq.getProfileIdx()};
		return this.jdbcTemplate.update(query, params);
	}

	// accountIdx 조회
	public int getAccountIdx(int profileIdx) {
		String query = "select accountIdx from Profile where profileIdx = ?";
		int params = profileIdx;
		return this.jdbcTemplate.queryForObject(query,
			int.class,
			params);
	}

	// profile 이름 조회
	public String getProfileName(int profileIdx) {
		String query = "select name from Profile where profileIdx = ?";
		int param = profileIdx;
		return this.jdbcTemplate.queryForObject(query, String.class, param);
	}

	public List<Profile.getProfileInfoResDto> getProfiles(int accountIdx) {
		String query = "select * from Profile where accountIdx =?";
		int params = accountIdx;
		return this.jdbcTemplate.query(query,
			(rs, rowNum) -> new Profile.getProfileInfoResDto(
				rs.getInt("profileIdx"),
				rs.getInt("status"),
				rs.getString("name"),
				rs.getString("language"),
				rs.getInt("ageGrade"),
				rs.getString("profilePhotoUrl")
			),
			params);
	}

	public List<Profile.getProfileInfoResDto> getProfilesByAccountIdx(int accountIdx) {
		String query = "select P.profileIdx, P.status, P.name, P.language, P.ageGrade, PP.profilePhotoUrl from Profile as P \n"
			+ "left join ProfilePhoto as PP\n"
			+ "on PP.profilePhotoIdx = P.profilePhotoIdx\n"
			+ "where accountIdx = ?";
		int params = accountIdx;
		return this.jdbcTemplate.query(query,
			(rs, rowNum) -> new Profile.getProfileInfoResDto(
				rs.getInt("profileIdx"),
				rs.getInt("status"),
				rs.getString("name"),
				rs.getString("language"),
				rs.getInt("ageGrade"),
				rs.getString("profilePhotoUrl")
			),
			params);
	}

	public Profile.getProfileInfoResDto getProfileInfo(int profileIdx) {
		String query = "select P.profileIdx, P.status, P.name, P.language, P.ageGrade, PP.profilePhotoUrl from Profile as P \n"
			+ "left join ProfilePhoto as PP\n"
			+ "on PP.profilePhotoIdx = P.profilePhotoIdx\n"
			+ "where profileIdx = ?";
		int params = profileIdx;
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new Profile.getProfileInfoResDto(
				rs.getInt("profileIdx"),
				rs.getInt("status"),
				rs.getString("name"),
				rs.getString("language"),
				rs.getInt("ageGrade"),
				rs.getString("profilePhotoUrl")
			),
			params);
	}

	public boolean checkIsValidAgeGradeForAdultContents(int profileIdx, int videoIdx) {
		String queryForProfile = "select ageGrade from Profile where profileIdx = ?";
		int profileAgeGrade = this.jdbcTemplate.queryForObject(queryForProfile,
			int.class,
			profileIdx);
		String queryForVideo = "select ageGrade from Video where videoIdx = ?";
		int videoAgeGrade = this.jdbcTemplate.queryForObject(queryForVideo,
			int.class,
			videoIdx);

		return videoAgeGrade == ADULT_AGE_GRADE && profileAgeGrade < ADULT_AGE_GRADE;
	}
}
