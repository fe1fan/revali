package io.xka.revali.core;

public interface RevaliConfiguration<T extends RevaliConfigurationTarget> {
    default void configure(T target) {
        target.setConfiguration(this);
    }
}

