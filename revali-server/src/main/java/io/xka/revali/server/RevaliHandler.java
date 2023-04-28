package io.xka.revali.server;

import io.xka.revali.core.serialization.SerializationAdopter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpMethod;

import java.util.function.Consumer;

public class RevaliHandler extends HttpServlet {

    protected static SerializationAdopter serializationAdopter;

    private void handle(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpMethod method;
        switch (req.getMethod()) {
            case "GET":
                method = HttpMethod.GET;
                break;
            case "POST":
                method = HttpMethod.POST;
                break;
            case "HEAD":
                method = HttpMethod.HEAD;
                break;
            case "PUT":
                method = HttpMethod.PUT;
                break;
            case "DELETE":
                method = HttpMethod.DELETE;
                break;
            case "OPTIONS":
                method = HttpMethod.OPTIONS;
                break;
            case "TRACE":
                method = HttpMethod.TRACE;
                break;
            default:
                method = HttpMethod.GET;
        }
        Consumer<RevaliHandlerControl> capture = RevaliMappings.capture(path, method);
        capture.accept(new RevaliHandlerControl(req, resp, serializationAdopter));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) {
        this.handle(req, resp);
    }
}
