package com.example.demo.src.account;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.account.domain.Account;

@Repository
public class AccountDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 회원가입
	public int createAccount(Account.createReqDto requestDto) {
		String createUserQuery = "insert into Account (password, email, phoneNumber, membership) VALUES (?,?,?,?)";
		Object[] createUserParams = new Object[] {requestDto.getPassword(), requestDto.getEmail(),
			requestDto.getPhoneNumber(), requestDto.getMembership()};
		this.jdbcTemplate.update(createUserQuery, createUserParams);

		String lastInsertedIdQuery = "select max(id) from Account";
		return this.jdbcTemplate.queryForObject(lastInsertedIdQuery, int.class);
	}

	// 이메일 확인
	public int checkIsDuplicatedEmail(String email) {
		String checkEmailQuery = "select exists(select email from Account where email = ?)";
		String checkEmailParams = email;
		return this.jdbcTemplate.queryForObject(checkEmailQuery,
			int.class,
			checkEmailParams);
	}
	// 이메일 확인
	public int checkIsDeactivatedAccount(String email) {
		String checkEmailQuery = "select status from Account where email = ?)";
		String checkEmailParams = email;
		return this.jdbcTemplate.queryForObject(checkEmailQuery,
			int.class,
			checkEmailParams);
	}
}
