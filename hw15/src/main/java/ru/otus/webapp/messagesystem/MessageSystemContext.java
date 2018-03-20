package ru.otus.webapp.messagesystem;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.messagesystem.MessageSystem;

public class MessageSystemContext {
    @Autowired
    private MessageSystem messageSystem;

    private Address frontAddress;
    private Address cacheAddress;

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getCacheAddress() {
        return cacheAddress;
    }

    public void setCacheAddress(Address cacheAddress) {
        this.cacheAddress = cacheAddress;
    }
}
