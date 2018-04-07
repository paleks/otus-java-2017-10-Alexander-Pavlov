package ru.otus.webapp.controller.socket;

import com.google.gson.Gson;
import ru.otus.webapp.app.Msg;
import ru.otus.webapp.app.MsgWorker;
import ru.otus.webapp.controller.websocket.MyWebSocket;
import ru.otus.webapp.messages.CacheInfoMsg;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgWorker implements MsgWorker {
    private static final Logger logger = Logger.getLogger(SocketMsgWorker.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    private MyWebSocket webSocket;

    public SocketMsgWorker(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        executor.shutdown();
        socket.close();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take();
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println();//line with json + an empty line
                logger.log(Level.INFO, "Message is sent to Message server");
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    logger.log(Level.INFO, "Message is received from Message server");
                    CacheInfoMsg cacheInfoMsg = new Gson().fromJson(inputLine, CacheInfoMsg.class);
                    this.webSocket.send(cacheInfoMsg.getCacheInfo());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void connectWithWebSocket(MyWebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
