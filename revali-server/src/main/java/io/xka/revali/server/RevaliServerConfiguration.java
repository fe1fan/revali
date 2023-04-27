package io.xka.revali.server;

import io.xka.revali.core.RevaliConfiguration;
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
}
