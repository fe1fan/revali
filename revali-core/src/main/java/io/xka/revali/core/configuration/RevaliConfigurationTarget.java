package io.xka.revali.core.configuration;

public interface RevaliConfigurationTarget {
    void setConfiguration(RevaliConfiguration<? extends RevaliConfigurationTarget> configuration);

    Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> getConfigurationClass();
}
