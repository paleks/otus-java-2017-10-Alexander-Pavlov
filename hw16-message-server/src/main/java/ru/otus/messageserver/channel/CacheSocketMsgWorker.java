package ru.otus.messageserver.channel;

import com.google.gson.Gson;
import ru.otus.messageserver.app.Msg;
import ru.otus.messageserver.messages.CacheInfoMsg;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.MessageSystemContext;
import ru.otus.messageserver.messagesystem.msg.MsgGetCacheAnswer;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CacheSocketMsgWorker extends SocketMsgWorker {
    private static final Logger logger = Logger.getLogger(CacheSocketMsgWorker.class.getName());

    public CacheSocketMsgWorker(Socket socket, MessageSystemContext context) {
        super(socket, context);
    }

    /**
     * Sends message to db + cache server
     */
    @Override
    protected void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println();//line with json + an empty line
                logger.info("Message is sent to database server" + json);
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Receives message from db + cache server and resends it to Message system
     */
    @Override
    protected void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("CACHE SOCKET: Message is received: " + inputLine);
                if (!inputLine.isEmpty()) {
                    logger.info("Message is received from web app and is sent to Message system " + inputLine);
                    CacheInfoMsg cacheInfoMsg = new Gson().fromJson(inputLine, CacheInfoMsg.class);
                    this.getMS().sendMessage(
                            new MsgGetCacheAnswer(this.getAddress(),
                                    this.getMessageSystemContext().getFrontAddress(), cacheInfoMsg.getCacheInfo()));
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public Address getAddress() {
        return this.getMessageSystemContext().getCacheAddress();
    }
}
