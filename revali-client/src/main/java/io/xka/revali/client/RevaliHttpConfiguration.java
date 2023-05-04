package io.xka.revali.client;

import io.xka.revali.core.configuration.RevaliConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevaliHttpConfiguration implements RevaliConfiguration<RevaliHttpClient> {

    private Pool pool;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pool {
        private int minIdle;
        private int maxTotal;
    }
}
