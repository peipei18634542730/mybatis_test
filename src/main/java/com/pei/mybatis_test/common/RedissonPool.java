package com.pei.mybatis_test.common;

import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * @ClassName RedisPool
 * @Deacription
 * @Author peipei
 * @Date 2020/5/20 14:29
 * @Version 1.0
 **/

public class RedissonPool {
    private static Config config = new Config();
    //声明redisso对象
    private static Redisson redisson = null;
    //实例化redisson
    static{

        config.useSingleServer().setAddress("127.0.0.1:6379");
        //得到redisson对象
        redisson = (Redisson) Redisson.create(config);

    }

    //获取redisson对象的方法
    public static Redisson getRedisson(){
        return redisson;
    }
}
