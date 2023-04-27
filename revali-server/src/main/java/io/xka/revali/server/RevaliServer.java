package io.xka.revali.server;

import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevaliServer {

    private final Logger logger = LoggerFactory.getLogger(RevaliServer.class);

    private String bind;
    private int port;
    private String[] args;

    private Server server;

    private boolean isRunning = false;


    public RevaliServer(String bind, int port, String[] args) {
        this.bind = bind;
        this.port = port;
        this.args = args;
    }

    public RevaliServerControl startup() {
        logger.info("Starting server on {}:{}", bind, port);
        //server
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(30, 10, 60000);
        this.server = new Server(queuedThreadPool);
        //connector
        ServerConnector connector = new ServerConnector(server, 8, 8);
        connector.setHost(bind);
        connector.setPort(port);
        connector.setAcceptQueueSize(100);
        this.server.addConnector(connector);
        //http
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath("/");
        ServletHolder servletHolder = servletHandler.addServlet(RevaliHandler.class, "/*");
        servletHolder.getRegistration().setMultipartConfig(
                new MultipartConfigElement(System.getProperty("java.io.tmpdir"))
        );
        this.server.setHandler(servletHandler);
        this.run();
        return new RevaliServerControl(this);
    }

    private synchronized void run() {
        if (this.isRunning) {
            throw new IllegalStateException("Server is already running");
        }
        this.server.setRequestLog((request, response) -> {
            logger.info("{} {} {} {} {} {} {}",
                    request.getRemoteAddr(),
                    request.getRemoteUser(),
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    response.getCommittedMetaData().getFields().get("Content-Length"),
                    response.getCommittedMetaData().getFields().get("Content-Type")
            );
        });
        try {
            this.server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    // when shutdown hook is called, the log4j2 logger is already shutdown, so we can't log anything
                    System.out.println("Shutting down server");
                    try {
                        this.server.stop();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
        );
        this.isRunning = true;
    }

    public void shutdown() {
        if (!this.isRunning) {
            throw new IllegalStateException("Server is not running");
        }
        try {
            this.server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.isRunning = false;
    }

    public void restart() {
        this.shutdown();
        this.run();
    }
}
