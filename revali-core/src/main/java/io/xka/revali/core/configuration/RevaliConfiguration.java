package io.xka.revali.core.configuration;

public interface RevaliConfiguration<T extends RevaliConfigurationTarget> {
    default void configure(T target) {
        target.setConfiguration(this);
    }
}

