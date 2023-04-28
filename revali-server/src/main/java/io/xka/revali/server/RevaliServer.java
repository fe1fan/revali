package io.xka.revali.server;

import io.xka.revali.core.configuration.RevaliConfiguration;
import io.xka.revali.core.configuration.RevaliConfigurationTarget;
import io.xka.revali.core.serialization.JacksonImpl;
import io.xka.revali.core.serialization.SerializationAdopter;
import jakarta.servlet.MultipartConfigElement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevaliServer implements RevaliConfigurationTarget {

    private final Logger logger = LoggerFactory.getLogger(RevaliServer.class);

    private RevaliServerConfiguration serverConfiguration;

    private Server server;

    private boolean isRunning = false;


    public RevaliServerControl startup() {
        final String bind = serverConfiguration.getHost();
        final int port = serverConfiguration.getPort();
        final String path = serverConfiguration.getPath();
        final int ioThreads = serverConfiguration.getThread().getIo();
        final int workerThreads = serverConfiguration.getThread().getWorker();


        logger.info("Starting server on {}:{}", bind, port);
        //server
        final int finalWorkThreads = Math.max(workerThreads, 17);
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(finalWorkThreads, finalWorkThreads, 60000);
        this.server = new Server(queuedThreadPool);
        //connector
        ServerConnector connector = new ServerConnector(server, ioThreads, ioThreads);
        connector.setHost(bind);
        connector.setPort(port);
        connector.setAcceptQueueSize(100);
        this.server.addConnector(connector);
        //http
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath(path);
        //serialize
        SerializationAdopter serializationAdopter = null;
        switch (serverConfiguration.getSerialization().getType()) {
            case JACKSON:
                serializationAdopter = new SerializationAdopter(new JacksonImpl());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + serverConfiguration.getSerialization().getType());
        }
        RevaliHandler.serializationAdopter = serializationAdopter;
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

    @Override
    public void setConfiguration(RevaliConfiguration<? extends RevaliConfigurationTarget> configuration) {
        this.serverConfiguration = (RevaliServerConfiguration) configuration;
    }

    @Override
    public Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> getConfigurationClass() {
        return RevaliServerConfiguration.class;
    }
}
