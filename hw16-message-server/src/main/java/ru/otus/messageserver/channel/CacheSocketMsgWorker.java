package ru.otus.messageserver.channel;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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
                //System.out.println("CACHE SOCKET: Message is sent to Message system... ");
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    protected void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                //System.out.println("CACHE SOCKET: Message is received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the messageserver
                    Gson gson = new Gson();
                    JsonReader reader = new JsonReader(new StringReader(stringBuilder.toString()));
                    reader.setLenient(true);

                    CacheInfoMsg cacheInfoMsg = new Gson().fromJson(reader, CacheInfoMsg.class);
                    this.getMS().sendMessage(
                            new MsgGetCacheAnswer(this.getAddress(),
                                    this.getMessageSystemContext().getFrontAddress(), cacheInfoMsg.getCacheInfo()));
                    stringBuilder.setLength(0);
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
