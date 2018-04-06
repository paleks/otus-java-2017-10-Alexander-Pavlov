package ru.otus.messageserver.messages;

import ru.otus.messageserver.app.CacheInfo;
import ru.otus.messageserver.app.Msg;

public class CacheInfoMsg extends Msg {
    private CacheInfo cacheInfo;

    public CacheInfoMsg(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    @Override
    public String toString() {
        return "CacheInfoMsg{" +
                "cacheInfo=" + cacheInfo +
                '}';
    }
}
