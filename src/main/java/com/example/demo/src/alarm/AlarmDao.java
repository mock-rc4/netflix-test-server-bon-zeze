package com.example.demo.src.alarm;

import com.example.demo.src.alarm.domain.GetAlarmRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlarmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<GetAlarmRes> getProfileAlarms(int profileIdx) {
        String query = "select alarmIdx, title, photoUrl, openDate from Alarm\n" +
                "join Video on Alarm.videoIdx = Video.videoIdx\n" +
                "where Video.openDate is not null and Video.openDate <= now() and Alarm.profileIdx = ?";

        return this.jdbcTemplate.query(query, videoRowMapper(), profileIdx);
    }

    private RowMapper<GetAlarmRes> videoRowMapper() {
        return (rs, rowNum) -> new GetAlarmRes(
                rs.getInt("alarmIdx"),
                rs.getString("title"),
                rs.getString("photoUrl"),
                rs.getTimestamp("openDate").toLocalDateTime()
        );
    }
}
