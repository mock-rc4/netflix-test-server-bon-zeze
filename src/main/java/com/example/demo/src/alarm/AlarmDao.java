package com.example.demo.src.alarm;

import com.example.demo.src.alarm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlarmDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(SetAlarmReq setAlarmReq) {
        String query = "insert into Alarm (profileIdx, videoIdx) values (?,?)";
        Object[] alarmParam = new Object[]{
                setAlarmReq.getProfileIdx(),
                setAlarmReq.getVideoIdx()};
        this.jdbcTemplate.update(query, alarmParam);

        String lastIdSql = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastIdSql, int.class);
    }

    public int update(SetAlarmReq setAlarmReq) {
        String query = "update Alarm set updatedAt = NOW(), status = ? where profileIdx = ? and videoIdx = ?";
        Object[] alarmParam = new Object[]{
                setAlarmReq.getStatus(),
                setAlarmReq.getProfileIdx(),
                setAlarmReq.getVideoIdx()};
        return this.jdbcTemplate.update(query, alarmParam);
    }

    public int checkAlarmExists(SetAlarmReq setAlarmReq) {
        String query = "select exists(select * from Alarm where profileIdx = ? and videoIdx = ?)";
        int profileIdx = setAlarmReq.getProfileIdx();
        int videoIdx = setAlarmReq.getVideoIdx();
        return this.jdbcTemplate.queryForObject(query, Integer.class, profileIdx, videoIdx);
    }


    public int getVideoAlarmSetting(SetAlarmReq setAlarmReq) {
        String query = "select status from Alarm where profileIdx = ? and videoIdx = ?";
        int profileIdx = setAlarmReq.getProfileIdx();
        int videoIdx = setAlarmReq.getVideoIdx();
        return this.jdbcTemplate.queryForObject(query, Integer.class, profileIdx, videoIdx);
    }

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
