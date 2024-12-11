package com.novisign.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProvider {
    private static final ObjectMapper mapper = new ObjectMapper();

    private ObjectMapperProvider() {}

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
