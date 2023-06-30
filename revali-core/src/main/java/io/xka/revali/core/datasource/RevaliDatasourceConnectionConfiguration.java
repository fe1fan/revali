package io.xka.revali.core.datasource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevaliDatasourceConnectionConfiguration {

    private String url;
    private String username;
    private String password;

    private Duration minIdle;

    private Integer minPoolSize;
    private Integer maxPoolSize;
    private Integer corePoolSize;

    private Duration connectionTimeout;
    private Integer idleTimeout;

    private String connectionTestQuery;
}
