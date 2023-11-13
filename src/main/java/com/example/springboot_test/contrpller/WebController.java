package com.example.springboot_test.contrpller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot_test.common.AuthAccess;
import com.example.springboot_test.common.Result;
import com.example.springboot_test.entity.User;
import com.example.springboot_test.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 功能：提供接口返回数据
 * 作者：胡霜福
 * 日期：2023/11/9 16:06
 */

@RestController
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess
    @RequestMapping("/")
    public Result hello() {
        return Result.success("success");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.error("数据输入不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }
    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())||StrUtil.isBlank(user.getRole())){
            return Result.error("数据输入不合法");
        }

        if(user.getUsername().length() > 10 || user.getPassword().length() > 10){
            return Result.error("数据输入不合法 注册名 或者 注册密码 长度不可以超过10");
        }

        user = userService.register(user);
        return Result.success(user);
    }

    /**
     *  Controller重置密码
     */
    @AuthAccess
    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }

}
