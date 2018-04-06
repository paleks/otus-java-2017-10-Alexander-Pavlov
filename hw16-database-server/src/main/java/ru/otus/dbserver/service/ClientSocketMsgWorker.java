package ru.otus.dbserver.service;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    public ClientSocketMsgWorker(String host, int port, DBService dbService) throws IOException {
        this(new Socket(host, port), dbService);
    }

    public ClientSocketMsgWorker(Socket socket, DBService dbService) throws IOException {
        super(socket, dbService);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
