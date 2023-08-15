package com.wsss.frame.jetty.helloworld;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

public class JettyClient {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.start();

        ContentResponse response = httpClient.GET("http://localhost:8080");

        System.out.println("Response status: " + response.getStatus());
        System.out.println("Response content: " + response.getContentAsString());

        httpClient.stop();
    }
}