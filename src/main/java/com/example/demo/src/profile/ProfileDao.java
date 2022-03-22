package com.example.demo.src.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.profile.domain.GetProfileReq;
import com.example.demo.src.profile.domain.Profile;

@Repository
public class ProfileDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ProfileDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int create(GetProfileReq getProfileReq) {
		String query = "insert into Profile (name, ageGrade, accountIdx, profilePhotoIdx) VALUES (?,?,?,?)";
		Object[] createParams = new Object[] {getProfileReq.getName(),
			getProfileReq.getAgeGrade(),
			getProfileReq.getAccountIdx(),
			getProfileReq.getProfilePhotoIdx()
		};
		this.jdbcTemplate.update(query, createParams);

		String lastInsertIdQuery = "select last_insert_id()";
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
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
				rs.getInt("profilePhotoIdx")
			),
			params);
	}

	public List<Profile.getProfileInfoResDto> getProfilesByAccountIdx(int accountIdx) {
		String query = "select * from Profile where accountIdx =?";
		int params = accountIdx;
		return this.jdbcTemplate.query(query,
			(rs, rowNum) -> new Profile.getProfileInfoResDto(
				rs.getInt("profileIdx"),
				rs.getInt("status"),
				rs.getString("name"),
				rs.getString("language"),
				rs.getInt("ageGrade"),
				rs.getInt("profilePhotoIdx")
			),
			params);
	}

	public Profile.getProfileInfoResDto getProfileInfo(int profileIdx) {
		String query = "select * from Profile where profileIdx=?";
		int params = profileIdx;
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new Profile.getProfileInfoResDto(
				rs.getInt("profileIdx"),
				rs.getInt("status"),
				rs.getString("name"),
				rs.getString("language"),
				rs.getInt("ageGrade"),
				rs.getInt("profilePhotoIdx")
			),
			params);
	}
}
