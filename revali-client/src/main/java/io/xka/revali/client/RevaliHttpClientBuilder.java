package io.xka.revali.client;

import io.xka.revali.core.TypeReference;
import io.xka.revali.core.configuration.RevaliConfiguration;
import io.xka.revali.core.configuration.RevaliConfigurationBuilder;

public class RevaliHttpClientBuilder {
    @SafeVarargs
    public static RevaliHttpClient configs(RevaliConfiguration<RevaliHttpClient>... configurations) {
        return RevaliConfigurationBuilder.configs(
                new TypeReference<>() {
                },
                configurations
        );
    }

    public static RevaliHttpClient yaml(String yaml) {
        return RevaliConfigurationBuilder.yaml(
                new TypeReference<>() {
                },
                yaml
        );
    }
}
