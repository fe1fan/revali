package io.xka.revali.server;

import io.xka.revali.core.RevaliConfiguration;
import io.xka.revali.core.RevaliConfigurationBuilder;
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
}