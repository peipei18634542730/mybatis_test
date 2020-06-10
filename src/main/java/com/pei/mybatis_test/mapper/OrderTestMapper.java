package com.pei.mybatis_test.mapper;

import com.pei.mybatis_test.pojo.OrderTest;

public interface OrderTestMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderTest record);

    int insertSelective(OrderTest record);

    OrderTest selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderTest record);

    int updateByPrimaryKey(OrderTest record);
}