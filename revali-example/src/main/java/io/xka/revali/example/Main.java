package io.xka.revali.example;

import io.xka.revali.server.RevaliServer;
import io.xka.revali.server.RevaliServerBuilder;
import io.xka.revali.server.RevaliServerConfiguration;
import io.xka.revali.server.RevaliServerControl;

public class Main {
    public static void main(String[] args) {
//        RevaliServer server = RevaliServerBuilder.configs(
//                RevaliServerConfiguration
//                        .builder()
//                        .host("127.0.0.1")
//                        .port(8888)
//                        .thread(
//                                RevaliServerConfiguration.Thread
//                                        .builder()
//                                        .io(8)
//                                        .worker(32)
//                                        .build()
//                        )
//                        .build()
//        );
        RevaliServer server = RevaliServerBuilder.yaml("bootstrap.yaml");

        RevaliServerControl startup = server.startup();
        startup.get("/test", control -> {
            control.result("hello world");
        });
    }
}