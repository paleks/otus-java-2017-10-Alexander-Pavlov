package ru.otus.webapp.messages;

import ru.otus.webapp.app.Msg;
import ru.otus.webapp.app.CacheInfo;

public class CacheInfoMsg extends Msg {
    private CacheInfo cacheInfo;

    public CacheInfoMsg(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public CacheInfoMsg() {
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }
}
