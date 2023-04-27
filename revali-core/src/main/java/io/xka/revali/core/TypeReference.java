package io.xka.revali.core;

import io.xka.revali.core.configuration.RevaliConfigurationTarget;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T extends RevaliConfigurationTarget> {
    private final Type type = this.getType(this.getClass());

    protected TypeReference() {
    }

    private Type getType(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return parameterized.getActualTypeArguments()[0];
        }
    }

    public Type getType() {
        return this.type;
    }
}
