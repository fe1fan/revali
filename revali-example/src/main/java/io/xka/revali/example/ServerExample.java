package io.xka.revali.example;

import io.xka.revali.server.RevaliServer;
import io.xka.revali.server.RevaliServerBuilder;
import io.xka.revali.server.RevaliServerControl;

import java.util.HashMap;

public class ServerExample {
    public static void main(String[] args) {
        RevaliServer server = RevaliServer.yaml("bootstrap.yaml");

        RevaliServerControl startup = server.startup();
        //echo server
        startup.get("/echo", control -> {
            control.result(control.getQuery("echo"));
        });
        startup.get("/test", control -> {
            control.result("hello world");
        });
        startup.get("/json", control -> {
            control.json(
                    new HashMap<>() {
                        {
                            put("hello", "world");
                        }
                    }
            );
        });
    }
}