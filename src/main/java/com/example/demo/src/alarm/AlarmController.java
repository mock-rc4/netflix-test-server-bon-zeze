package com.example.demo.src.alarm;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.alarm.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmProvider alarmProvider;
    private final AlarmService alarmService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AlarmController(AlarmProvider alarmProvider, AlarmService alarmService) {
        this.alarmProvider = alarmProvider;
        this.alarmService = alarmService;
    }

    @PostMapping("")
    public BaseResponse<Integer> createAlarms(@RequestBody SetAlarmReq setAlarmReq) {
        try {
            int alarmIdx = alarmService.createAlarms(setAlarmReq);
            return new BaseResponse<>(alarmIdx);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("")
    public BaseResponse<String> updateAlarms(@RequestBody SetAlarmReq setAlarmReq) {
        try {
            if (alarmProvider.checkAlarmExists(setAlarmReq) == 0) { //알림 켜짐,꺼짐 상관 없음.
                createAlarms(setAlarmReq);
            } else if (alarmService.updateAlarms(setAlarmReq) == 0) {
                return new BaseResponse<>("fail to update");
            }
            return new BaseResponse<>("success to update");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("")
    public BaseResponse<Integer> getVideoAlarmSetting(@RequestBody SetAlarmReq setAlarmReq) {
        try {
            if (alarmProvider.checkAlarmExists(setAlarmReq) == 0) {
                return new BaseResponse<>(0); //GET_ALARM_EXISTS_ERROR 변경 가능- create 호출
            }
            int alarmStatus = alarmProvider.getVideoAlarmSetting(setAlarmReq);
            return new BaseResponse<>(alarmStatus);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{profileIdx}")
    public BaseResponse<List<GetAlarmRes>> getProfileAlarms(@PathVariable int profileIdx) {
        try {
            List<GetAlarmRes> alarmRes = alarmProvider.getProfileAlarms(profileIdx);
            return new BaseResponse<>(alarmRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
