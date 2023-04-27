package io.xka.revali.server;

import io.xka.revali.core.configuration.RevaliConfiguration;
import io.xka.revali.core.configuration.RevaliConfigurationBuilder;
import io.xka.revali.core.TypeReference;

public class RevaliServerBuilder {
    @SafeVarargs
    public static RevaliServer configs(RevaliConfiguration<RevaliServer>... configurations) {
        return RevaliConfigurationBuilder.configs(
                new TypeReference<>() {
                },
                configurations
        );
    }

    public static RevaliServer yaml(String yaml) {
        return RevaliConfigurationBuilder.yaml(
                new TypeReference<>() {
                },
                yaml
        );
    }
}