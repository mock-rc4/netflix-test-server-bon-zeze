package com.example.demo.src.account;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.src.account.domain.PatchAccountReq;
import com.example.demo.src.account.domain.PatchPasswordReq;
import com.example.demo.src.account.domain.PostLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        String query = "insert into Account (password, email, maileSubscriptionAgreement) VALUES (?,?,?)";
        Object[] params = new Object[]{requestDto.getPassword(), requestDto.getEmail(), requestDto.getEmail_subscription_agreement()};
        this.jdbcTemplate.update(query, params);

        String lastInsertedIdQuery = "select max(accountIdx) from Account";
        return this.jdbcTemplate.queryForObject(lastInsertedIdQuery, int.class);
    }

    // 이메일 확인
    public int checkExistsEmail(String email) {
        String query = "select exists(select email from Account where email = ?)";
        String params = email;
        return this.jdbcTemplate.queryForObject(query, int.class, params);
    }

	// 이메일 확인
	public String checkHasMembership(String email) {
		String query = "select membership from Account where email = ?";
		String params = email;
		return this.jdbcTemplate.queryForObject(query,
			String.class,
			params);
	}

    // 핸드폰 확인
    public int checkExistsPhoneNumber(String phone) {
        String query = "select exists(select phoneNumber from Account where phoneNumber = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, phone);
    }

	// 유효한 계정 식별 ID(accountIdx)인지 확인
	public int checkIsValidAccountIdx(int accountIdx) {
		String query = "select exists(select accountIdx from Account where accountIdx = ?)";
		int params = accountIdx;
		return this.jdbcTemplate.queryForObject(query, int.class, params);
	}

    // 이메일로 탈퇴 회원인지 확인
    public int checkIsDeactivatedAccount(String email) {
        String query = "select exists(select status from Account where email = ?)";
        String params = email;
        return this.jdbcTemplate.queryForObject(query, int.class, params);
    }

    // 회원 비활성화
    public int deactivateAccount(Account.DeactivateReqDto deactivateReq) {
        String query = "update Account set status = ?, updatedAt = NOW() where accountIdx = ?";
        Object[] params = new Object[]{0, deactivateReq.getAccountIdx()};
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
                        rs.getString("socialLoginType"),
						rs.getString("membershipStartDate")
                ),
                params);
    }


    public String getMembership(int accountIdx) {
        String query = "select membership from Account where accountIdx = ?";
        return this.jdbcTemplate.queryForObject(query, String.class, accountIdx);
    }

    public Account getPasswordByAccountIdx(PatchPasswordReq patchPasswordReq){
        String getPasswordQuery = "select * from Account where accountIdx = ?";
        int param = patchPasswordReq.getAccountIdx();

        return this.jdbcTemplate.queryForObject(getPasswordQuery, accountRowMapper(), param);
    }

    public Account getPasswordByEmail(PostLoginReq postLoginReq) {
        String getPasswordQuery = "select * from Account where email = ?";
        String param = postLoginReq.getEmailOrPhone();

        return accountRowMapper(getPasswordQuery, param);
    }

    public Account getPasswordByPhone(PostLoginReq postLoginReq) {
        String getPasswordQuery = "select * from Account where phoneNumber = ?";
        String param = postLoginReq.getEmailOrPhone();

        return accountRowMapper(getPasswordQuery, param);
    }

    public int updateEmail(PatchAccountReq patchAccountReq) {
        String updateQuery = "update Account set updatedAt = now() , email = ? where accountIdx = ?";

        return updateAccount(patchAccountReq, updateQuery);
    }

    public int updatePassword(PatchPasswordReq patchPasswordReq) {
        String updateQuery = "update Account set updatedAt = now() , password = ? where accountIdx = ?";
        Object[] updateParams = new Object[]{patchPasswordReq.getNewPassword(), patchPasswordReq.getAccountIdx()};

        return this.jdbcTemplate.update(updateQuery, updateParams);
    }

    public int updatePhoneNumber(PatchAccountReq patchAccountReq) {
        String updateQuery = "update Account set updatedAt = now() , phoneNumber = ? where accountIdx = ?";

        return updateAccount(patchAccountReq, updateQuery);
    }

    public int updateMemberShip(PatchAccountReq patchAccountReq) {
        String updateQuery = "update Account set updatedAt = now() , membership = ? where accountIdx = ?";

        return updateAccount(patchAccountReq, updateQuery);
    }

    private int updateAccount(PatchAccountReq patchAccountReq, String updateQuery) {
        Object[] updateParams = new Object[]{patchAccountReq.getUpdateParam(), patchAccountReq.getAccountIdx()};

        return this.jdbcTemplate.update(updateQuery, updateParams);
    }

    private Account accountRowMapper(String query, String param){
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Account(
                        rs.getInt("accountIdx"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("membership"),
                        rs.getString("socialLoginIdx"),
                        rs.getString("socialLoginType"),
                        rs.getInt("status")
                ),
                param
        );
    }

    private RowMapper<Account> accountRowMapper() {
        return  (rs, rowNum) -> new Account(
                rs.getInt("accountIdx"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getString("membership"),
                rs.getString("socialLoginIdx"),
                rs.getString("socialLoginType"),
                rs.getInt("status")
        );
    }
}
