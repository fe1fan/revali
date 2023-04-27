package io.xka.revali.server;

import io.xka.revali.core.RevaliConfiguration;

public class RevaliServerBuilder {
    @SafeVarargs
    public static RevaliServer configs(RevaliConfiguration<RevaliServer>... configurations) {
        RevaliServer revaliServer = new RevaliServer();
        for (RevaliConfiguration<RevaliServer> configuration : configurations) {
            configuration.configure(revaliServer);
        }
        return revaliServer;
    }
}