package com.novisign.demo.service.validator;

import com.novisign.demo.exception.ValidationException;

public interface Rule<T> {
    void validate(T entity) throws ValidationException;

}
