package com.pei.mybatis_test.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisPool
 * @Deacription jedis 池
 * @Author peipei
 * @Date 2020/5/20 15:04
 * @Version 1.0
 **/

public class RedisPool {
    private static JedisPool pool;//jedis连接池

    private static int maxTotal = 20;//最大连接数

    private static int maxIdle = 10;//最大空闲连接数

    private static int minIdle = 5;//最小空闲连接数

    private static boolean testOnBorrow = true;//在取连接时测试连接的可用性

    private static boolean testOnReturn = false;//再还连接时不测试连接的可用性

    static {
        initPool();//初始化连接池
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void close(Jedis jedis){
        jedis.close();
    }

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);

        config.setBlockWhenExhausted(true);

//Idle时进行连接扫描
        config.setTestWhileIdle(true);
//表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
//表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);

        pool = new JedisPool(config, "192.168.189.129", 6379, 100000);
    }
}
