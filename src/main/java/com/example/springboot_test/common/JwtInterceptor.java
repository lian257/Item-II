package com.example.springboot_test.common;

/**
 * 功能：
 * 作者：胡霜福
 * 日期：2023/11/11 1:44
 */
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot_test.entity.User;
import com.example.springboot_test.exception.ServiceException;
import com.example.springboot_test.mapper.UserMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        //header token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
            //url 参数 ?token=xx
        }
        // 如果不是映射到方法直接通过

        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            // 解码
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "请登录");
        }
        // 根据token中的userid查询数据库
        User user = userMapper.selectById(Integer.valueOf(userId));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }
        // 用户密码加签验证 token 生成验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
            // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录");
        }
        return true;
    }
}