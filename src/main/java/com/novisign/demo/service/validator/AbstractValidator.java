package com.novisign.demo.service.validator;

import java.util.ArrayList;
import java.util.List;

import com.novisign.demo.exception.ValidationException;

abstract public class AbstractValidator<T> {

    protected T entity;

    private final List<Rule<T>> rules = new ArrayList<>();

    public void addRules(List<Rule<T>> rules) {
        this.rules.addAll(rules);
    }

    public AbstractValidator<T> withEntity(T entity) {
        this.entity = entity;
        return this;
    }

    public void validate() throws ValidationException {
        for (Rule<T> rule : rules) {
            rule.validate(this.entity);
        }
    }

}
