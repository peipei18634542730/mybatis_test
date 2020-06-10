package com.pei.mybatis_test.mapper.mapperExt;

import com.pei.mybatis_test.mapper.OrderTestMapper;
import com.pei.mybatis_test.pojo.OrderTest;
import org.apache.ibatis.annotations.Param;

public interface OrderTestMapperExt extends OrderTestMapper {
   OrderTest queryOrdByOrdNum(@Param("ordNum") String ordNum);
}