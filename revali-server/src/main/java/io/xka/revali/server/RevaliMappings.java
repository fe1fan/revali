package io.xka.revali.server;

import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevaliMappings {

    /**
     * all mappings
     */
    private final static Map<String, Map<HttpMethod, Consumer<RevaliHandlerControl>>> MAPPINGS = new HashMap<>(8);

    /**
     * default handler
     */
    private final static Consumer<RevaliHandlerControl> DEFAULT = (control) -> {
        try (PrintWriter writer = control.getResponse().getWriter()) {
            writer.println("{\"error\":\"no handler found\"}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * customize handler
     */
    private static Consumer<RevaliHandlerControl> CUSTOMIZE = null;


    /**
     * set customize handler
     *
     * @param customize customize handler
     */
    public static void setCustomize(Consumer<RevaliHandlerControl> customize) {
        CUSTOMIZE = customize;
    }

    /**
     * register new handler
     *
     * @param path    http path
     * @param method  http method
     * @param handler handler function
     */
    public synchronized static void register(String path, HttpMethod method, Consumer<RevaliHandlerControl> handler) {
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("path cannot be null or empty");
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        Map<HttpMethod, Consumer<RevaliHandlerControl>> consumers = MAPPINGS.getOrDefault(path, new HashMap<>(8));
        if (consumers.containsKey(method)) {
            throw new RuntimeException("path already registered: " + path);
        }
        consumers.put(method, handler);
        MAPPINGS.put(path, consumers);
    }

    /**
     * capture handler
     *
     * @param path   http path
     * @param method http method
     * @return handler function or default handler
     */
    public static Consumer<RevaliHandlerControl> capture(String path, HttpMethod method) {
        Map<HttpMethod, Consumer<RevaliHandlerControl>> consumers = MAPPINGS.get(path);
        if (consumers != null) {
            Consumer<RevaliHandlerControl> consumer = consumers.get(method);
            return consumer == null ? CUSTOMIZE == null ? DEFAULT : CUSTOMIZE : consumer;
        }
        //starts with
        return CUSTOMIZE == null ? DEFAULT : CUSTOMIZE;
    }

}
