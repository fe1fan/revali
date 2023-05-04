package io.xka.revali.example;

import io.xka.revali.client.RevaliHttpClient;
import io.xka.revali.client.RevaliHttpClientBuilder;
import io.xka.revali.client.RevaliHttpClientControl;

public class ClientExample {
    public static void main(String[] args) {
        RevaliHttpClient revaliHttpClient = RevaliHttpClientBuilder.yaml("bootstrap-cli.yaml");
        RevaliHttpClientControl control = revaliHttpClient.getControl();
        String s = control.get("https://www.baidu.com");
        System.out.println("response from baidu:");
        System.out.println(s);
        control.close();
    }
}
