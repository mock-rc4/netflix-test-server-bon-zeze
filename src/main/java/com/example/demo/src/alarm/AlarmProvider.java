package com.example.demo.src.alarm;

import com.example.demo.config.BaseException;
import com.example.demo.src.alarm.domain.GetAlarmRes;
import com.example.demo.src.alarm.domain.SetAlarmReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AlarmProvider {

    @Autowired
    private final AlarmDao alarmDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlarmProvider(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    public List<GetAlarmRes> getProfileAlarms(int profileIdx) throws BaseException {
        try {
            return alarmDao.getProfileAlarms(profileIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAlarmExists(SetAlarmReq setAlarmReq) throws BaseException {
        try {
            return alarmDao.checkAlarmExists(setAlarmReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Integer getVideoAlarmSetting(SetAlarmReq setAlarmReq) throws BaseException {
        try {
            return alarmDao.getVideoAlarmSetting(setAlarmReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
