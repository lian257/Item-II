package com.example.springboot_test.exception;

import lombok.Getter;

/**
 * 功能：
 * 作者：胡霜福
 * 日期：2023/11/10 17:12
 */
@Getter
public class ServiceException extends RuntimeException {

    private final  String code;
    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}