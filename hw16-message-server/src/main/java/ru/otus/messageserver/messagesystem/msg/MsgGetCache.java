package ru.otus.messageserver.messagesystem.msg;

import ru.otus.messageserver.channel.CacheSocketMsgWorker;
import ru.otus.messageserver.messages.CacheInfoMsg;
import ru.otus.messageserver.messagesystem.Address;

public class MsgGetCache extends MsgToCache {

    public MsgGetCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(CacheSocketMsgWorker cacheSocketWorker) {
        cacheSocketWorker.send(new CacheInfoMsg(true));
    }
}
