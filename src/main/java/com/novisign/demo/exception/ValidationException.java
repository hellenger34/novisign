package com.novisign.demo.exception;

import com.novisign.demo.api.enums.ExceptionType;

public class ValidationException extends AbstractException {

    public ValidationException(ExceptionDetail exceptionDetail, Object... params) {
        super(ExceptionType.VALIDATION_ERROR, exceptionDetail.getFormattedMessage(params), exceptionDetail.getCode());
    }

}
