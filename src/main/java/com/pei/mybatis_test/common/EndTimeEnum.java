package com.pei.mybatis_test.common;

/**
 * @ClassName EndTimeEnum
 * @Deacription TODO
 * @Author peipei
 * @Date 2020/5/21 11:17
 * @Version 1.0
 **/

public enum EndTimeEnum {

    ten(1000*60*10,"十分钟"),
    twenty(1000*60*20,"二十分钟"),
    thirty(1000*60*30,"三十分钟"),
    forty(1000*60*40,"四十分钟"),
    fifty(1000*60*50,"五十分钟"),
    sixty(1000*60*60,"六十分钟");


    private long time;
    private String time_name;
    EndTimeEnum(long time, String time_name) {
        this.time = time;
        this.time_name = time_name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }
}
