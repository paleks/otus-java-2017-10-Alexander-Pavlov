package ru.otus.webapp.controller.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.webapp.messages.CacheInfoMsg;
import ru.otus.webapp.app.CacheInfo;
import ru.otus.webapp.controller.socket.SocketMsgWorker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class MyWebSocket {
    private static final Logger logger = Logger.getLogger(MyWebSocket.class.getName());

    private static final int MESSAGE_SERVER_PORT = 5050;

    private Session session;

    private SocketMsgWorker socketWorker;

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.socketWorker.send(new CacheInfoMsg());
        logger.log(Level.INFO, "GetCache message is sent to Message server");
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException {
        setSession(session);

        this.socketWorker = new SocketMsgWorker("localhost", MESSAGE_SERVER_PORT);
        this.socketWorker.connectWithWebSocket(this);
        this.socketWorker.init();

        logger.log(Level.INFO, "Web socket is opened, socket worker is inited");
    }

    public Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) throws IOException {
        this.socketWorker.close();
        logger.log(Level.INFO, "Web socket, socket are closed");
    }

    public void send(CacheInfo info) throws IOException {
        this.session.getRemote().sendString(new Gson().toJson(info));
        logger.log(Level.INFO, "CacheInfo is sent to browser: " + info.toString());
    }
}
