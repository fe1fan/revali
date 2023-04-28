package io.xka.revali.client;

import io.xka.revali.core.configuration.RevaliConfiguration;
import io.xka.revali.core.configuration.RevaliConfigurationTarget;

public class RevaliHttpClient extends RevaliHttpClientBuilder implements RevaliConfigurationTarget {

    private RevaliHttpConfiguration configuration;

    @Override
    public void setConfiguration(RevaliConfiguration<? extends RevaliConfigurationTarget> configuration) {
        this.configuration = (RevaliHttpConfiguration) configuration;
    }

    @Override
    public Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> getConfigurationClass() {
        return RevaliHttpConfiguration.class;
    }
}
