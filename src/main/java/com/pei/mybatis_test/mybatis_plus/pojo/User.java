package com.pei.mybatis_test.mybatis_plus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.zookeeper.Op;

import java.io.File;
import java.util.Date;

/**
 * @ClassName User
 * @Deacription
 * @Author peipei
 * @Date 2020/6/3 13:41
 * @Version 1.0
 **/
@Data
public class User {
    @TableId(type = IdType.ID_WORKER)
    private long id;
    private String name;
    private int age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    @Version
    private Integer version;
    @TableLogic
    private Integer isDel;
}
