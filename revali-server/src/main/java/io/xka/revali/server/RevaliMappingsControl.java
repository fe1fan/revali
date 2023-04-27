package io.xka.revali.server;

import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class RevaliMappingsControl {

    private final Logger logger = LoggerFactory.getLogger(RevaliMappingsControl.class);

    public void get(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register get path: {}", path);
        RevaliMappings.register(path, HttpMethod.GET, handler);
    }

    public void post(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register post path: {}", path);
        RevaliMappings.register(path, HttpMethod.POST, handler);
    }

    public void put(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register put path: {}", path);
        RevaliMappings.register(path, HttpMethod.PUT, handler);
    }

    public void delete(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register delete path: {}", path);
        RevaliMappings.register(path, HttpMethod.DELETE, handler);
    }

    public void patch(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register patch path: {}", path);
        RevaliMappings.register(path, HttpMethod.PATCH, handler);
    }

    public void options(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register options path: {}", path);
        RevaliMappings.register(path, HttpMethod.OPTIONS, handler);
    }

    public void head(String path, Consumer<RevaliHandlerControl> handler) {
        logger.info("register head path: {}", path);
        RevaliMappings.register(path, HttpMethod.HEAD, handler);
    }
}
