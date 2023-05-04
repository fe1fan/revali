package io.xka.revali.client;

import io.xka.revali.core.configuration.RevaliConfiguration;
import io.xka.revali.core.configuration.RevaliConfigurationTarget;
import org.eclipse.jetty.client.HttpClient;

public class RevaliHttpClient extends RevaliHttpClientBuilder implements RevaliConfigurationTarget {

    private RevaliHttpClientConfiguration configuration;

    public RevaliHttpClientControl getControl() {
        return new RevaliHttpClientControl(new HttpClient());
    }

    @Override
    public void setConfiguration(RevaliConfiguration<? extends RevaliConfigurationTarget> configuration) {
        this.configuration = (RevaliHttpClientConfiguration) configuration;
    }

    @Override
    public Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> getConfigurationClass() {
        return RevaliHttpClientConfiguration.class;
    }
}
