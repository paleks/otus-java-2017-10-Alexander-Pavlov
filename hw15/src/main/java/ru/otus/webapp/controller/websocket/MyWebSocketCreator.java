package ru.otus.webapp.controller.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.springframework.beans.factory.annotation.Autowired;

public class MyWebSocketCreator implements WebSocketCreator {

    @Autowired
    MyWebSocket myWebSocket;

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return myWebSocket;
    }
}
