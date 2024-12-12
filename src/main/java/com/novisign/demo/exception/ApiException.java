package com.novisign.demo.exception;

import com.novisign.demo.api.enums.ExceptionType;

import lombok.Data;

@Data
public class ApiException {

    private String message;
    private Integer code;
    private ExceptionType exceptionType;

    public ApiException(String message, Integer code, ExceptionType exceptionType) {
        this.message = message;
        this.exceptionType = exceptionType;
        this.code = code;
    }
}
