package com.pei.mybatis_test.controller;

import com.pei.mybatis_test.common.EndTimeEnum;
import com.pei.mybatis_test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderController
 * @Deacription
 * @Author peipei
 * @Date 2020/5/20 15:25
 * @Version 1.0
 **/
@RestController
    public class OrderController {
    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/addOrder",method = RequestMethod.GET)
    public String addOrder() {
        return orderService.addOrder();
    }

    @RequestMapping(value = "subtractNum",method = RequestMethod.GET)
    public String subOrderNum(@RequestParam(name="ordNum") String ordNum) {
        return orderService.subtractOrderCurrent(ordNum);
    }
    @RequestMapping(value = "shutDown",method = RequestMethod.GET)
    public void getTime(int time) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("shutdown /s /t  0");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },time, TimeUnit.MINUTES);
    }
}
