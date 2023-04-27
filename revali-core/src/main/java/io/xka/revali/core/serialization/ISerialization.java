package io.xka.revali.core.serialization;

public interface ISerialization {
    <T> T toObj(String json, Class<T> clazz);
    String toJson(Object obj);
}
