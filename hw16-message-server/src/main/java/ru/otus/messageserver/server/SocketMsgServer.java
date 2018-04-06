package ru.otus.messageserver.server;

import ru.otus.messageserver.channel.CacheSocketMsgWorker;
import ru.otus.messageserver.channel.FrontSocketMsgWorker;
import ru.otus.messageserver.channel.SocketMsgWorker;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.MessageSystem;
import ru.otus.messageserver.messagesystem.MessageSystemContext;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SocketMsgServer {
    private static final Logger logger = Logger.getLogger(SocketMsgServer.class.getName());

    private static final int FRONT_PORT = 5050;
    private static final int CACHE_PORT = 5051;

    private static final String CLIENT_START_COMMAND = "java -jar ../../hw16-database-server/target/dbserver.jar";

    private final List<SocketMsgWorker> workers;
    private final List<Process> processes;
    private final MessageSystem messageSystem;

    private int counter = 0;

    public SocketMsgServer() {
        this.workers = new ArrayList<>();
        this.processes = new ArrayList<>();

        this.messageSystem = new MessageSystem();
    }

    public void start() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(CLIENT_START_COMMAND.split(" "));

        try (ServerSocket cacheSocketServer = new ServerSocket(CACHE_PORT);
             ServerSocket frontSocketServer = new ServerSocket(FRONT_PORT)) {
            while (true) {
                logger.info("Message server waites frontend connections on port: " + frontSocketServer.getLocalPort());
                Socket frontSocket = frontSocketServer.accept();

                logger.info("New front is connected...");

                this.processes.add(pb.start());
                logger.info("New database server is started in a separate process...");

                Socket cacheSocket = cacheSocketServer.accept();
                logger.info("New cache is connected...");

                MessageSystemContext context = new MessageSystemContext(this.messageSystem);

                context.setFrontAddress(new Address("Front" + String.valueOf(counter)));
                context.setCacheAddress(new Address("Cache" + String.valueOf(counter)));

                SocketMsgWorker frontClient = new FrontSocketMsgWorker(frontSocket, context);
                SocketMsgWorker cacheClient = new CacheSocketMsgWorker(cacheSocket, context);

                this.messageSystem.addAddressee(frontClient);
                this.messageSystem.addAddressee(cacheClient);

                logger.info("Addressee are added: " + frontClient.getAddress().getId() + ", " + cacheClient.getAddress().getId());

                frontClient.init();
                this.workers.add(frontClient);

                cacheClient.init();
                this.workers.add(cacheClient);

                counter++;
            }
        } finally {
            this.processes.forEach(Process::destroy);

            for (SocketMsgWorker worker : this.workers) {
                worker.close();
            }

            this.messageSystem.dispose();
        }
    }
}
