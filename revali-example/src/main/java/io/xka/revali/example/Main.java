package io.xka.revali.example;

import io.xka.revali.server.RevaliServer;
import io.xka.revali.server.RevaliServerBuilder;
import io.xka.revali.server.RevaliServerControl;

public class Main {
    public static void main(String[] args) {
        RevaliServer server = RevaliServerBuilder.config("127.0.0.1", 8888).build();
        RevaliServerControl startup = server.startup();
        startup.get("/test", control -> {
            control.result("hello world");
        });
        startup.restart();
    }
}