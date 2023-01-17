package org.musala.drones.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
    public static final ObjectMapper INSTANCE = new ObjectMapper();

    private JacksonUtil() {
        //singleton
    }
}
