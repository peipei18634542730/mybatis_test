package com.pei.mybatis_test.service;

import com.pei.mybatis_test.pojo.OrderTest;
import org.springframework.core.annotation.Order;

/**
 * @ClassName OrderService
 * @Deacription TODO
 * @Author peipei
 * @Date 2020/5/20 13:30
 * @Version 1.0
 **/

public interface OrderService {
    /*秒杀*/
    String subtractOrderCurrent(String orderNum);

    /*查库存*/
    OrderTest queryOrderNum(String id);

    /*创建订单*/
    String addOrder();
    /*减库存*/
    String subtractOrder(OrderTest order);
}
