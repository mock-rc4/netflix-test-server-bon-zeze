package com.example.demo.src.naverAccount;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.src.naverAccount.domain.NaverAccount;

@Repository
public class NaverAccountDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 이메일 확인
	public int checkNaverAccountByEmail(String email) {
		String checkEmailQuery = "select exists(select email from Account where email = ?)";
		String checkEmailParams = email;
		return this.jdbcTemplate.queryForObject(checkEmailQuery,
			int.class,
			checkEmailParams);
	}

	public NaverAccount.GetNaverAccountResDto getNaverAccountById(String id) {
		String query = "select accountIdx, email from Account where socialLoginIdx = ?";
		String params = id;
		return this.jdbcTemplate.queryForObject(query,
			(rs, rowNum) -> new NaverAccount.GetNaverAccountResDto(
				rs.getInt("accountIdx"),
				rs.getString("email")
			), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
			params
		); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
	}

	public int createNaverAccount(NaverAccount.PostSignInNaverAccountReqDto requestDto) {
		String query = "insert into Account (email, password, socialLoginIdx, socialLoginType) VALUES (?,?,?,?)"; // 실행될 동적 쿼리문
		Object[] params = new Object[] {requestDto.getEmail(), requestDto.getPassword(),
			requestDto.getSocialLoginIdx(),
		requestDto.getSocialLoginType()};
		this.jdbcTemplate.update(query, params);
		String lastInserIdQuery = "select max(accountIdx) from Account";
		return this.jdbcTemplate.queryForObject(lastInserIdQuery,
			int.class);
	}

}