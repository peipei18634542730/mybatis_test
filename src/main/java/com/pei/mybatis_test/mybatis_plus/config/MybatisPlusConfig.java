package com.pei.mybatis_test.mybatis_plus.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName MybatisPlusConfig
 * @Deacription
 * @Author peipei
 * @Date 2020/6/3 15:31
 * @Version 1.0
 **/

@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    /*乐观锁插件*/
    @Bean
    OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
    /*分页插件*/
    @Bean
    PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    /*逻辑删除*/
    ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}
