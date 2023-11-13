package com.example.springboot_test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 功能：
 * 作者：胡霜福
 * 日期：2023/11/9 21:26
 */
@TableName("user")
@Data
public class User {
    @TableId(type= IdType.AUTO)

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;
    /**
     * 多的字段
     */
    @TableField(exist = false)
    private String token;
}