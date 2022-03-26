package com.example.demo.src.googleAccount;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.googleAccount.domain.GoogleAccount;

@Repository
public class GoogleAccountDao {
	private static final String GOOGLE = "Google";
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	public int checkGoogleAccountByEmail(String email) {
		String checkEmailQuery = "select exists(select email from Account where email = ? and socialLoginType = ?)";
		return this.jdbcTemplate.queryForObject(checkEmailQuery,
			int.class,
			email, GOOGLE);
	}

	public int createGoogleAccount(GoogleAccount.PostSignInGoogleAccountReqDto requestDto) {
		String query = "insert into Account (email, password, socialLoginIdx, socialLoginType) VALUES (?,?,?,?)"; // 실행될 동적 쿼리문
		Object[] params = new Object[] {requestDto.getEmail(), requestDto.getPassword(),
			requestDto.getSocialLoginIdx(),
			requestDto.getSocialLoginType()};
		this.jdbcTemplate.update(query, params);
		String lastInserIdQuery = "select max(accountIdx) from Account";
		return this.jdbcTemplate.queryForObject(lastInserIdQuery,
			int.class);
	}

	public GoogleAccount.GetGoogleAccountResDto getGoogleAccountById(String id) {
		String query = "select accountIdx, email from Account where socialLoginIdx = ? and socialLoginType = ?";
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new GoogleAccount.GetGoogleAccountResDto(
				rs.getInt("accountIdx"),
				rs.getString("email")
			),
			id, GOOGLE
		);
	}
}
