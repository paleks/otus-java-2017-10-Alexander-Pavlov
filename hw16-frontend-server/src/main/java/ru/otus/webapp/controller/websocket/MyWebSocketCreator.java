package ru.otus.webapp.controller.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.springframework.context.ApplicationContext;

public class MyWebSocketCreator implements WebSocketCreator {

    ApplicationContext context;

    public MyWebSocketCreator(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return context.getBean("myWebSocket");
    }
}
