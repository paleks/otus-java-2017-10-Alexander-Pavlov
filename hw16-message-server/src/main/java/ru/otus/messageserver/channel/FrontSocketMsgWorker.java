package ru.otus.messageserver.channel;

import com.google.gson.Gson;
import ru.otus.messageserver.app.Msg;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.MessageSystemContext;
import ru.otus.messageserver.messagesystem.msg.MsgGetCache;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontSocketMsgWorker extends SocketMsgWorker {
    private static final Logger logger = Logger.getLogger(FrontSocketMsgWorker.class.getName());

    public FrontSocketMsgWorker(Socket socket, MessageSystemContext context) {
        super(socket, context);
    }

    /**
     * Sends message to web app
     */
    @Override
    protected void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.poll();
                if (msg != null) {
                    String json = new Gson().toJson(msg);
                    out.println(json);
                    out.println();//line with json + an empty line
                    logger.info("Message is sent to Web app " + json);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Receives message from frontend and sends message to message system
     */
    @Override
    protected void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                this.getMS().sendMessage(
                        new MsgGetCache(this.getAddress(), this.getMessageSystemContext().getCacheAddress()));
                logger.info("Message is received from web app and is sent to Message system");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public Address getAddress() {
        return this.getMessageSystemContext().getFrontAddress();
    }
}
