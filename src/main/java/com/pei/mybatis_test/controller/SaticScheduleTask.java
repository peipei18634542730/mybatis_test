package com.pei.mybatis_test.controller;

import com.pei.mybatis_test.common.EndTimeEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @ClassName SaticScheduleTask
 * @Deacription
 * @Author peipei
 * @Date 2020/5/21 11:15
 * @Version 1.0
 **/
//@Configuration
//@EnableScheduling
public class SaticScheduleTask {

    public long getTime(int time) {
        long num = 0;
        switch (time) {
            case 10:
                num= EndTimeEnum.ten.getTime();
                break;
            case 20:
                num = EndTimeEnum.twenty.getTime();
                break;
            case 30:
                num = EndTimeEnum.thirty.getTime();
                break;
            case 40:
                num = EndTimeEnum.forty.getTime();
                break;
            case 50:
                num = EndTimeEnum.fifty.getTime();
                break;
            case 60:
                num = EndTimeEnum.sixty.getTime();
                break;
            default:
                num =0 ;
                break;
        }
    return num;
    }

//    @Scheduled(fixedRate = 1000)
//    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//    }

}
