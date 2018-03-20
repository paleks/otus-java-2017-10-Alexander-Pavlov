package ru.otus.webapp.controller.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.webapp.services.cache.CacheInfo;
import ru.otus.webapp.services.front.FrontendService;

import java.io.IOException;

@WebSocket
public class MyWebSocket {
    private Session session;

    @Autowired
    private FrontendService frontendService;

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.frontendService.handleRequest();
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("onClose");
    }

    public void send(CacheInfo info) throws IOException {
        this.session.getRemote().sendString(new Gson().toJson(info));
    }
}
