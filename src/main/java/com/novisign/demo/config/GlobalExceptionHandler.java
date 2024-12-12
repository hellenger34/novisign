package com.novisign.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.novisign.demo.api.enums.ExceptionType;
import com.novisign.demo.exception.ApiException;
import com.novisign.demo.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation exception to represent user-friendly message
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        ApiException apiException = new ApiException(e.getMessage(), e.getCode(), e.getExceptionType());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle unexpected exception to hide details of implementation
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e) {
        ApiException apiException = new ApiException("Unexpected error", null, ExceptionType.INTERNAL_ERROR);
        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
