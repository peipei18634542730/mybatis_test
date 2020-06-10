package com.pei.mybatis_test.service.impl;

import com.pei.mybatis_test.common.RedisPool;
import com.pei.mybatis_test.common.util.RedisPoolUtil;
import com.pei.mybatis_test.mapper.OrderTestMapper;
import com.pei.mybatis_test.mapper.mapperExt.OrderTestMapperExt;
import com.pei.mybatis_test.pojo.OrderTest;
import com.pei.mybatis_test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @ClassName OrderServiceImpl
 * @Deacription
 * @Author peipei
 * @Date 2020/5/20 13:34
 * @Version 1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTestMapperExt orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String subtractOrderCurrent(String orderNum) {
        System.out.println("进入iPhone120手机抢购频道");
        long l = System.currentTimeMillis();
        OrderTest orderTest = orderMapper.queryOrdByOrdNum(orderNum);
        orderTest.setNum(orderTest.getNum() - 1);
        /*卖超了*/
        if (orderTest.getNum() < 0) {
            long l1 = System.currentTimeMillis();
            String use_time = "用时"+(l - l1)+"\t iphone120已经被抢光";
            System.out.println(use_time);
            return use_time;
        }
        String lock = orderNum + (Math.random()*900 + 100);
        System.out.println(Thread.currentThread().getName()+"\t 开始尝试加锁");
        Long result = RedisPoolUtil.setnx(lock, String.valueOf(System.currentTimeMillis() + 5000));
        if (result != null && result.intValue() ==1) {
            System.out.println(Thread.currentThread().getName()+"\t 获取锁");
            RedisPoolUtil.expire(lock,5);
            /*减库存*/
            String s = subtractOrder(orderTest);
            /*释放锁*/
            RedisPoolUtil.del(lock);
            System.out.println(Thread.currentThread().getName()+"\t 释放锁");
            return s;
        } else {
            String lockValue = RedisPoolUtil.get(lock);
            if (lockValue != null && Long.parseLong(lockValue) >= System.currentTimeMillis()) {//如果value的值大于当前时间，说明还没过期
                String lockValueB = RedisPoolUtil.getSet(lock, String.valueOf(System.currentTimeMillis() + 5000));
                if (lockValue == null || lockValue.equals(lockValueB)) {
                    /*枷锁成功*/
                    RedisPoolUtil.expire(lock,5);
                    subtractOrder(orderTest);
                    RedisPoolUtil.del(lock);
                    System.out.println(Thread.currentThread().getName()+"\t 释放锁");
                }
            }
        }

        return "库存删除成功";
    }

    @Override
    public OrderTest queryOrderNum(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrder() {
        String id = UUID.randomUUID().toString();
        int num = 50;
        /*参数封装*/
        OrderTest order =new OrderTest();
        order.setNum(num);
        order.setOrderId(id);
        order.setOrderNum("123456");
        /*redis记录库存和订单编号*/
        String result = RedisPoolUtil.getSet("123456", num + "");
        int insert = orderMapper.insert(order);
        if (insert<1 || result == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "库存创建失败";
        }
        return "库存创建成功";
    }

    @Override
    public String subtractOrder(OrderTest orderTest) {
        int i = orderMapper.updateByPrimaryKeySelective(orderTest);
        String result = RedisPoolUtil.getSet(orderTest.getOrderNum(), orderTest.getNum()+"");
        if (i<1 || StringUtils.isEmpty(result)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "库存删除失败";
        }
        System.out.println("减库存,剩余库存+"+orderTest.getNum());
        return "库存删除成功";
    }

}
