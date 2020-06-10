package com.pei.mybatis_test;

import com.pei.mybatis_test.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableCaching
@SpringBootApplication
@MapperScan(basePackages = {"com.pei.mybatis_test.mapper","com.pei.mybatis_test.mybatis_plus.mapper"})
public class MybatisTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(MybatisTestApplication.class, args);
    }

}
