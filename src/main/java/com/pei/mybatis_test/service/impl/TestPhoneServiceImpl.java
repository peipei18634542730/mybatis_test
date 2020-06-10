package com.pei.mybatis_test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName TestPhoneServiceImpl
 * @Deacription
 * @Author peipei
 * @Date 2020/5/22 17:35
 * @Version 1.0
 **/
@Service
public class TestPhoneServiceImpl {
    private static String  SUCCESS = "通过此手机号注册成功";
    private static String  FAIL_PHONE = "通知本手机号无法注册，提示为非法手机号";
    private static String  FAIL_PEOPLE = "提示此手机号已经被其他用户注册";
    private static String  FAIL_ADDRESS = "此手机号码为中国大陆非法手机号码";
    /*模拟dao层访问数据库*/
    List<People>peoples;
    public String register(String phoneNum) {
        Pattern compile = Pattern.compile("^[1][3,4,5,7,8][0-9]$");
        Matcher matcher = compile.matcher(phoneNum);
        boolean matches = matcher.matches();
        if (!matches) return FAIL_ADDRESS;
        if (isMobile(phoneNum)) {
            if (CollectionUtils.isEmpty(peoples)) {
                People people = new People();
                people.setName("张三");
                people.setPhoneNum(phoneNum);
                peoples.add(people);
                return SUCCESS;
            }
            for (People people : peoples) {
                if (phoneNum.equals(people.getPhoneNum())) return FAIL_PEOPLE;
            }
        }
        return FAIL_PHONE;
    }

    /**
     * 手机号验证
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    class People{
        private String name;
        private String phoneNum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }
    }
}
