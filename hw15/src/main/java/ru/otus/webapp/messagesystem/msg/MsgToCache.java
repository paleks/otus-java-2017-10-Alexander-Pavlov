package ru.otus.webapp.messagesystem.msg;

import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.messagesystem.Addressee;
import ru.otus.webapp.messagesystem.Message;
import ru.otus.webapp.services.cache.CacheEngine;

public abstract class MsgToCache extends Message {
    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheEngine) {
            exec((CacheEngine) addressee);
        }
    }

    public abstract void exec(CacheEngine cacheEngine);
}
