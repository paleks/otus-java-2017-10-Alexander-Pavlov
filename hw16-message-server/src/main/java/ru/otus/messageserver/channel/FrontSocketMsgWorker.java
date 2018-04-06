package ru.otus.messageserver.channel;

import com.google.gson.Gson;
import ru.otus.messageserver.app.Msg;
import ru.otus.messageserver.messages.CacheInfoMsg;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.Addressee;
import ru.otus.messageserver.messagesystem.MessageSystem;
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
                //Msg msg = output.take(); //blocks
                Msg msg = output.poll();
                if (msg != null) {
                    String json = new Gson().toJson(msg);
                    out.println(json);
                    out.println();//line with json + an empty line
                    //System.out.println("FRONT SOCKET: Message is sent to Message system: " + json);
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
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                CacheInfoMsg cacheInfoMsg = new Gson().fromJson(inputLine, CacheInfoMsg.class);
                //System.out.println("FRONT SOCKET: Message is received: " + inputLine);
                this.getMS().sendMessage(
                        new MsgGetCache(this.getAddress(), this.getMessageSystemContext().getCacheAddress()));
//                this.context.getMessageSystem().sendMessage(
//                        new MsgGetCacheAnswer(this.getAddress(), this.context.getFrontAddress(), cacheInfoMsg.getCacheInfo()));
                stringBuilder.setLength(0);
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
