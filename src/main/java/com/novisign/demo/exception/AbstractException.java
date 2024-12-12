package com.novisign.demo.exception;

import com.novisign.demo.api.enums.ExceptionType;

import lombok.Getter;

@Getter
public class AbstractException extends Exception {

    private ExceptionType exceptionType;
    private String message;
    private Integer code;

    public AbstractException(ExceptionType exceptionType, String message, Integer code) {
        this.exceptionType = exceptionType;
        this.message = message;
        this.code = code;
    }
}
