package com.example.demo.src.alarm;

import com.example.demo.config.BaseException;
import com.example.demo.src.alarm.domain.SetAlarmReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AlarmService {

    private final AlarmDao alarmDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AlarmService(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    public int createAlarms(SetAlarmReq setAlarmReq) throws BaseException {
        try {
            return alarmDao.create(setAlarmReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int updateAlarms(SetAlarmReq setAlarmReq) throws BaseException {
        try {
            return alarmDao.update(setAlarmReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
