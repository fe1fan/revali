package io.xka.revali.server;

public class RevaliServerControl extends RevaliMappingsControl {
    private final RevaliServer rserver;

    public RevaliServerControl(RevaliServer rserver) {
        this.rserver = rserver;
    }

    public void shutdown() {
        rserver.shutdown();
    }
    public void restart() {
        rserver.restart();
    }
}
