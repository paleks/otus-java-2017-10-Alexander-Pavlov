package ru.otus.dbserver.messages;

import ru.otus.dbserver.app.Msg;
import ru.otus.dbserver.cache.CacheInfo;

public class CacheInfoMsg extends Msg {
    private CacheInfo cacheInfo;

    public CacheInfoMsg(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }
}
