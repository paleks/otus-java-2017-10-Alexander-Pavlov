package ru.otus.messageserver.channel;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.json.simple.parser.ParseException;
import ru.otus.messageserver.app.Msg;
import ru.otus.messageserver.messages.CacheInfoMsg;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.Addressee;
import ru.otus.messageserver.messagesystem.MessageSystem;
import ru.otus.messageserver.messagesystem.MessageSystemContext;
import ru.otus.messageserver.messagesystem.msg.MsgGetCache;
import ru.otus.messageserver.messagesystem.msg.MsgGetCacheAnswer;
import ru.otus.messageserver.messagesystem.msg.MsgToCache;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontSocketMsgWorker extends SocketMsgWorker implements Addressee {
    private static final Logger logger = Logger.getLogger(FrontSocketMsgWorker.class.getName());

    private final MessageSystemContext context;

    private boolean isActive = true;

    public FrontSocketMsgWorker(Socket socket, MessageSystemContext context) {
        super(socket);
        this.context = context;
    }

    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Sends message to web app
     *
     * TODO
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
                if (cacheInfoMsg.isAlive()) {
                    //System.out.println("FRONT SOCKET: Message is received: " + inputLine);
                    this.context.getMessageSystem().sendMessage(
                            new MsgGetCache(this.context.getFrontAddress(), this.context.getCacheAddress()));
                } else {
                    // is ready to be deleted
                    this.isActive = false;
                }
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
        return context.getFrontAddress();
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
