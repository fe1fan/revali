package io.xka.revali.server;

import io.xka.revali.core.configuration.RevaliConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevaliServerConfiguration implements RevaliConfiguration<RevaliServer> {
    @Builder.Default
    private String host = "127.0.0.1";
    @Builder.Default
    private int port = 8080;
    @Builder.Default
    private String path = "/";
    @Builder.Default
    private Thread thread = new Thread();
    @Builder.Default
    private Serialization serialization = new Serialization();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Thread {
        @Builder.Default
        private int io = 1;
        @Builder.Default
        private int worker = 10;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Serialization {

        @Builder.Default
        private Type type = Type.JACKSON;

        public enum Type {
            JACKSON
        }

    }
}
