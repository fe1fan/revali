package io.xka.revali.core.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonImpl implements ISerialization {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T toObj(String json, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public String toJson(Object obj) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
