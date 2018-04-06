package ru.otus.messageserver;

import ru.otus.messageserver.server.SocketMsgServer;

public class MessageServerMain {

    public static void main(String[] args) throws Exception {
        new MessageServerMain().start();
    }

    private void start() throws Exception {
        SocketMsgServer server = new SocketMsgServer();
        server.start();
    }
}
