package com.example.demo.src.kakaoAccount;

import com.example.demo.src.kakaoAccount.domain.PostKakaoAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KakaoAccountDao {
    private static final String KAKAO = "Kakao";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PostKakaoAccount getAccountBySocialId(String id) {
        String query = "select accountIdx, email from Account where socialLoginIdx = ? and socialLoginType = ?";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new PostKakaoAccount(
                        rs.getInt("accountIdx"),
                        rs.getString("email")
                ),
                id, KAKAO
        );
    }

    public Integer getAccountByEmail(String email) {
        String query = "select accountIdx from Account where email = ?";
        return this.jdbcTemplate.queryForObject(query, Integer.class, email);
    }
}
