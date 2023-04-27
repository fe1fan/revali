package io.xka.revali.core;

public interface RevaliConfigurationTarget {
    void setConfiguration(RevaliConfiguration<? extends RevaliConfigurationTarget> configuration);

    Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> getConfigurationClass();
}
