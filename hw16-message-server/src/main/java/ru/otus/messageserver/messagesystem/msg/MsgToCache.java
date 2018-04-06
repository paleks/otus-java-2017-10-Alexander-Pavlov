package ru.otus.messageserver.messagesystem.msg;

import ru.otus.messageserver.channel.CacheSocketMsgWorker;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.Addressee;
import ru.otus.messageserver.messagesystem.Message;

public abstract class MsgToCache extends Message {
    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheSocketMsgWorker) {
            exec((CacheSocketMsgWorker) addressee);
        }
    }

    public abstract void exec(CacheSocketMsgWorker cacheSocketWorker);
}
