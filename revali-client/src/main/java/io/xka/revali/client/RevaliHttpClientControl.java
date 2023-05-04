package io.xka.revali.client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

public class RevaliHttpClientControl {

    private final HttpClient httpClient;

    public RevaliHttpClientControl(HttpClient httpClient) {
        this.httpClient = httpClient;
        try {
            this.httpClient.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            httpClient.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String url) {
        String result = null;
        try {
            ContentResponse contentResponse = httpClient.GET(url);
            System.out.println(contentResponse);
            result = contentResponse.getContentAsString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
