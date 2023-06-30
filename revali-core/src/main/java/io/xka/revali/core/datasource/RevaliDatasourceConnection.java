package io.xka.revali.core.datasource;

import java.util.function.Consumer;

public interface RevaliDatasourceConnection {

    RevaliDatasourceConnection connected(RevaliDatasourceConnectionConfiguration configuration);

    boolean isAlive();

    void close();

    <T> void submit(String sql, Consumer<T> callback);

    <T> T execute(String sql);
}
