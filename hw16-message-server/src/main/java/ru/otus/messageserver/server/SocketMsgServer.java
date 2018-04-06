package ru.otus.messageserver.server;

import ru.otus.messageserver.app.MsgWorker;
import ru.otus.messageserver.channel.CacheSocketMsgWorker;
import ru.otus.messageserver.channel.FrontSocketMsgWorker;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.MessageSystem;
import ru.otus.messageserver.messagesystem.MessageSystemContext;
import ru.otus.messageserver.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgServer {
    private static final Logger logger = Logger.getLogger(SocketMsgServer.class.getName());

    private static final int FRONT_PORT = 5050;
    private static final int CACHE_PORT = 5051;

    private static final String CLIENT_START_COMMAND = "java -jar ../../hw16-database-server/target/dbserver.jar";
    private static final int CLIENT_START_DELAY_SEC = 2;

    private final List<MsgWorker> fronts;
    private final List<MsgWorker> caches;
    private final MessageSystem messageSystem;

    private ExecutorService executor;

    private int counter = 0;

    public SocketMsgServer() {
        fronts = new CopyOnWriteArrayList<>();
        caches = new CopyOnWriteArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();

        this.messageSystem = new MessageSystem();
    }

    public void start() throws Exception {
        this.executor.submit(this::startExecutor);

        ServerSocket cacheSocketServer = new ServerSocket(CACHE_PORT);
        ServerSocket frontSocketServer = new ServerSocket(FRONT_PORT);

        while (true) {
            logger.info("Message server waites frontend connections on port: " + frontSocketServer.getLocalPort());
            Socket frontSocket = frontSocketServer.accept();

            logger.info("New front is connected...");

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            startDBServer(executorService);
            logger.info("New database server process is started...");

            Socket cacheSocket = cacheSocketServer.accept();
            logger.info("New cache is connected...");

            MessageSystemContext context = new MessageSystemContext(this.messageSystem);

            context.setFrontAddress(new Address("Front" + String.valueOf(counter)));
            context.setCacheAddress(new Address("Cache" + String.valueOf(counter)));

            FrontSocketMsgWorker frontClient = new FrontSocketMsgWorker(frontSocket, context);
            CacheSocketMsgWorker cacheClient = new CacheSocketMsgWorker(cacheSocket, context);

            // init message system
            this.messageSystem.addAddressee(frontClient);
            this.messageSystem.addAddressee(cacheClient);
            logger.info("Addressee are added: " +
                    frontClient.getAddress().getId() + ", " + cacheClient.getAddress().getId());
            //this.messageSystem.dispose();
            //this.messageSystem.start();

            frontClient.init();
            fronts.add(frontClient);

            cacheClient.init();
            caches.add(cacheClient);

            counter++;
        }
    }

    private void startExecutor() {
        while (true) {
            for (MsgWorker front : fronts) {
                if (front)
            }
        }
    }

//    @Override
//    public boolean getRunning() {
//        return true;
//    }
//
//    @Override
//    public void setRunning(boolean running) {
//        if (!running) {
//            executor.shutdown();
//            logger.info("Bye.");
//        }
//    }

    private void startDBServer(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}
