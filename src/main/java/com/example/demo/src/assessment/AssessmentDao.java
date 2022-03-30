package com.example.demo.src.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.assessment.domain.Assessment;

@Repository
public class AssessmentDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public AssessmentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public int checkHasAssessment(Assessment.createOrModifyDto requestDto) {
		int profileIdx = requestDto.getProfileIdx();
		int videoIdx = requestDto.getVideoIdx();
		int status = requestDto.getStatus();
		String query = "select exists(select AssessmentIdx from Assessment where profileIdx = ? and videoIdx = ? and status = ?)";
		return this.jdbcTemplate.queryForObject(query, Integer.class, profileIdx, videoIdx, status);
	}


	public int createAssessment(Assessment.createOrModifyDto requestDto) {
		String query = "insert into Assessment (profileIdx, videoIdx, status) VALUES (?,?, ?)";
		Object[] params = new Object[] {requestDto.getProfileIdx(), requestDto.getVideoIdx(), requestDto.getStatus()};
		this.jdbcTemplate.update(query, params);

		String lastInsertedIdQuery = "select max(assessmentIdx) from Assessment";
		return this.jdbcTemplate.queryForObject(lastInsertedIdQuery, int.class);
	}

	public void modifyAssessment(Assessment.createOrModifyDto requestDto) {
		String query = "update Assessment set profileIdx = ?, videoIdx = ?, status = ?, updatedAt = NOW()";
		Object[] params = new Object[]{requestDto.getProfileIdx(), requestDto.getVideoIdx(), requestDto.getStatus()};
		this.jdbcTemplate.update(query, params);
	}

	public int getAssessmentStatus(int profileIdx, int videoIdx) {
		String query = "select status from Assessment where profileIdx = ? and videoIdx = ?";
		return this.jdbcTemplate.queryForObject(query,
			int.class,
			profileIdx, videoIdx);
	}

	public int checkIsDuplicatedEmail(String email) {
		String query = "select exists(select email from Account where email = ?)";
		String params = email;
		return this.jdbcTemplate.queryForObject(query,
			int.class,
			params);
	}

}