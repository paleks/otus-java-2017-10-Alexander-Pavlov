package ru.otus.webapp.messagesystem.msg;

import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.services.cache.CacheEngine;
import ru.otus.webapp.services.cache.CacheInfo;

public class MsgGetCache extends MsgToCache {

    public MsgGetCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(CacheEngine cacheEngine) {
        CacheInfo info = cacheEngine.getCacheInfo();
        cacheEngine.getMS().sendMessage(new MsgGetCacheAnswer(getTo(), getFrom(), info));
    }
}
