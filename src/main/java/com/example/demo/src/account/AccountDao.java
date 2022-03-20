package com.example.demo.src.account;

import java.util.List;

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
		String query = "insert into Account (password, email, phoneNumber, membership) VALUES (?,?,?,?)";
		Object[] params = new Object[] {requestDto.getPassword(), requestDto.getEmail(),
			requestDto.getPhoneNumber(), requestDto.getMembership()};
		this.jdbcTemplate.update(query, params);

		String lastInsertedIdQuery = "select max(accountIdx) from Account";
		return this.jdbcTemplate.queryForObject(lastInsertedIdQuery, int.class);
	}

	// 이메일 확인
	public int checkIsDuplicatedEmail(String email) {
		String query = "select exists(select email from Account where email = ?)";
		String params = email;
		return this.jdbcTemplate.queryForObject(query,
			int.class,
			params);
	}

	// 이메일 확인
	public int checkIsDeactivatedAccount(String email) {
		String query = "select exists(select status from Account where email = ?)";
		String params = email;
		return this.jdbcTemplate.queryForObject(query,
			int.class,
			params);
	}

	// 회원 비활성화
	public int deactivateAccount(Account.DeactivateReqDto deactivateReq) {
		String query = "update Account set status = ?, updatedAt = NOW() where accountIdx = ?";
		Object[] params = new Object[] {0, deactivateReq.getAccountIdx()};
		return this.jdbcTemplate.update(query, params);
	}

	// 전체 계정들의 정보 조회
	public List<Account.getAccountsDto> getAccounts() {
		String query = "select * from Account";
		return this.jdbcTemplate.query(query,
			(rs, rowNum) -> new Account.getAccountsDto(
				rs.getInt("accountIdx"),
				rs.getString("email"),
				rs.getString("phoneNumber"),
				rs.getString("membership"))
		);
	}

	// 해당 membership을 갖는 계정들의 정보 조회
	public List<Account.getAccountsDto> getAccountsByMembership(String membership) {
		String query = "select * from Account where membership =?";
		String params = membership;
		return this.jdbcTemplate.query(query,
			(rs, rowNum) -> new Account.getAccountsDto(
				rs.getInt("accountIdx"),
				rs.getString("email"),
				rs.getString("phoneNumber"),
				rs.getString("membership")
			),
			params);
	}

	// 해당 accountIdx를 갖는 계정을 조회
	public Account.getResDto getAccount(int accountIdx) {
		String query = "select * from Account where accountIdx = ?";
		int params = accountIdx;
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new Account.getResDto(
				rs.getInt("accountIdx"),
				rs.getString("password"),
				rs.getString("email"),
				rs.getString("phoneNumber"),
				rs.getString("membership"),
				rs.getString("socialLoginIdx"),
				rs.getString("socialLoginType")
			),
			params);
	}
}
