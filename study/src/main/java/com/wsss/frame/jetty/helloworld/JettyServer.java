package com.wsss.frame.jetty.helloworld;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServerConnector connector = new ServerConnector(server);
        connector.setIdleTimeout(100L);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});

        // Set a handler
        HandlerCollection handlerc =new HandlerCollection();
        handlerc.setHandlers(new Handler[]{new HelloHandler()});
        server.setHandler(handlerc);
        server.start();
        server.join();
    }

    static class HelloHandler extends AbstractHandler {
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println("Hello from Server!");
        }
    }
}

