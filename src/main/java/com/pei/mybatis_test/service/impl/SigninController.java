package com.pei.mybatis_test.service.impl;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @ClassName SigninController
 * @Deacription
 * @Author peipei
 * @Date 2020/5/22 17:59
 * @Version 1.0
 **/
@Controller
public class SigninController {

//    @Autowired
//    Register register;

    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(@RequestParam(name = "phoneNum") String phoneNum) {
        RegisterServiceImpl register = new RegisterServiceImpl(); // 模拟注入service
       return register.registerTo(phoneNum);
    }
    @Service
    static class RegisterServiceImpl{
        private static String SUCCESS = "通过此手机号注册成功";
        private static String FAIL_PHONE = "通知本手机号无法注册，提示为非法手机号";
        private static String FAIL_PEOPLE = "提示此手机号已经被其他用户注册";
        private static String FAIL_ADDRESS = "此手机号码为中国大陆非法手机号码";
        final String  filePath = "D:/abc.txt";
        /*模拟dao层访问数据库*/
        List<People> peoples = new ArrayList<>();
        public String registerTo(String phoneNum) {

            int i = Integer.parseInt(phoneNum.substring(0,1));
            if (i > 1) {
                return FAIL_ADDRESS;
            }
            char[] chars = phoneNum.toCharArray();
            StringBuffer s = new StringBuffer();
            for (Character aChar : chars) {
                if (!Character.isSpaceChar(aChar)) {
                    s.append(aChar);
                }
            }
            String s1 = s.toString();
            if (isMobile(s1)) {
                //模拟将数据从数据库取出
                String peoplesFile = readeFile(filePath);
                List<People> people1 = JSONArray.parseArray(peoplesFile, People.class);
                if (CollectionUtils.isEmpty(people1)) {
                    People people = new People();
                    people.setName("张三");
                    people.setPhoneNum(s1);
                    peoples.add(people);

                } else {
                    for (People people : people1) {
                        if (s1.equals(people.getPhoneNum())) {
                            return FAIL_PEOPLE;
                        } else {
                            People people2 = new People();
                            people2.setName("里斯");
                            people2.setPhoneNum(phoneNum);
                            peoples.add(people);
                        }
                    }
                }
                //模拟将数据存入数据库
                String peoplesJ = JSONArray.toJSON(peoples).toString();
                writeFile(filePath,peoplesJ);
                return SUCCESS;

            }
            return FAIL_PHONE;
        }
        /**
         * FileInputStream类的使用：读取文件内容 模拟mapper/dao查询方法
         * @param filePath
         * @return
         */
        private String readeFile(String filePath) {
            FileInputStream input = null;
            String result = "";
            try {
                //1.根据path实例化一个输入流的对象
                input = new FileInputStream(filePath);
                //2.返回这个输入流中可以被读的剩下的bytes字节的估计值；
                int size = input.available();
                //3.根据输入流的字节创建一个byte数组
                byte[] array = new byte[size];
                //4.把数据读取到byte数组中
                input.read(array);
                //5.根据获取的byte数组新建一个字符串，然后输出
                result = new String(array);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(input != null){
                    try {
                        //关闭
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
        /**
         * FileOutputStream类的使用：内容写入到文件中 模拟mapper/dao保存方法
         * @param filePath
         * @return
         */
        private void writeFile(String filePath,String content) {
            FileOutputStream out = null;
            try {
                //1.根据路径创建输出流对象
                out = new FileOutputStream(filePath) ;
                //2.把String字符串转换成byte数组；
                byte[] b = content.getBytes();
                //3.把byte数组输出
                out.write(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
//        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
//                "|(18[0-9])|(19[8,9]))\\d{8}$";
//        Pattern p = Pattern.compile(regExp);
//        Matcher m = p.matcher(str);
//        return m.matches();
        String regEx = "(\\s)*(\\d(\\s)*){11}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }

   static class People {
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






    public static void main(String[] args) {
        /**
         * TODO 请从这里开始补充代码
         *
         * 测试1：138 1234 1234
         *	结果：通过此手机号注册成功
         *
         * 测试2：13812345abc
         *	结果：通知本手机号无法注册，提示为非法手机号
         *
         * 测试3：13812345678
         *	结果：通知此手机号注册成功
         *
         * 测试4：138 1234 5678
         *	结果：提示此手机号已经被其他用户注册
         *
         * 测试5：98765432101
         *	结果：此手机号码为中国大陆非法手机号码
         */
        SigninController s = new SigninController();
        System.out.println(s.register("138 1234 5678"));

    }
}