package io.xka.revali.core.serialization;

public class SerializationAdopter {

    private final ISerialization serialization;

    public SerializationAdopter(ISerialization serialization) {
        this.serialization = serialization;
    }

    public <T> T toObj(String json, Class<T> clazz) {
        return serialization.toObj(json, clazz);
    }

    public String toJson(Object obj) {
        return serialization.toJson(obj);
    }
}
