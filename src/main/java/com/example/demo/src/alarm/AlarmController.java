package com.example.demo.src.alarm;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.alarm.domain.GetAlarmRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
