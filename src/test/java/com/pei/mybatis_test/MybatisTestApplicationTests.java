package com.pei.mybatis_test;

import com.pei.mybatis_test.mybatis_plus.mapper.UserMapper;
import com.pei.mybatis_test.mybatis_plus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class MybatisTestApplicationTests {

    @Autowired
    UserMapper userMapper;
    /*查询*/
    @Test
    void getUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users.get(0));
    }

@Test
    void addUser() {
        User u= new User();
        u.setAge(11);
        u.setEmail("12312");
        u.setName("你好啊");

    int insert = userMapper.insert(u);
    System.out.println(insert);
}
}
