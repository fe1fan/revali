package io.xka.revali.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevaliServerBuilder {
    private ServerConfig config;

    public static RevaliServerBuilder config(String bind, int port) {
        return RevaliServerBuilder.builder()
                .config(
                        ServerConfig.builder()
                                .bind(bind)
                                .port(port)
                                .build()
                )
                .build();
    }

    public RevaliServer build() {
        return new RevaliServer(
                config.getBind(),
                config.getPort(),
                new String[]{}
        );
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ServerConfig {
    private String bind;
    private int port;
}
