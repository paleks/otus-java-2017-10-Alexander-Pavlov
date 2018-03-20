package ru.otus.webapp.services.front;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.webapp.messagesystem.MessageSystemContext;
import ru.otus.webapp.services.cache.CacheInfo;
import ru.otus.webapp.messagesystem.msg.MsgGetCache;
import ru.otus.webapp.controller.websocket.MyWebSocket;
import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.messagesystem.Message;
import ru.otus.webapp.messagesystem.MessageSystem;

import java.io.IOException;

public class FrontendServiceImpl implements FrontendService {
    private Address address;

    @Autowired
    private MessageSystemContext context;

    @Autowired
    private MyWebSocket myWebSocket;

    public FrontendServiceImpl() {
        this.address = new Address("Frontend");
    }

    public void init() {
        context.setFrontAddress(this.address);
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequest() {
        Message message = new MsgGetCache(getAddress(), context.getCacheAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void handleResponce(CacheInfo info) throws IOException {
        this.myWebSocket.send(info);
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
