package ru.otus.messageserver.channel;

import ru.otus.messageserver.app.Msg;
import ru.otus.messageserver.app.MsgWorker;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.Addressee;
import ru.otus.messageserver.messagesystem.MessageSystem;
import ru.otus.messageserver.messagesystem.MessageSystemContext;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public abstract class SocketMsgWorker implements MsgWorker, Addressee {
    private static final Logger logger = Logger.getLogger(SocketMsgWorker.class.getName());
    private static final int WORKERS_COUNT = 2;

    protected final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    protected final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    protected final ExecutorService executor;
    protected final Socket socket;

    private final MessageSystemContext context;

    public SocketMsgWorker(Socket socket, MessageSystemContext context) {
        this.socket = socket;
        this.context = context;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Blocks
    abstract protected void sendMessage();
    @Blocks
    abstract protected void receiveMessage();

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
        this.socket.close();
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    public MessageSystemContext getMessageSystemContext() {
        return this.context;
    }

    @Override
    public Address getAddress() {
        return null;
    }

    @Override
    public MessageSystem getMS() {
        return this.context.getMessageSystem();
    }
}
