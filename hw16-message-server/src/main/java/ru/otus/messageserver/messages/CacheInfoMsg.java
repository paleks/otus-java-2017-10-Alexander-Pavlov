package ru.otus.messageserver.messages;

import ru.otus.messageserver.app.CacheInfo;
import ru.otus.messageserver.app.Msg;

public class CacheInfoMsg extends Msg {
    private CacheInfo cacheInfo;
    private boolean isAlive;

    public CacheInfoMsg(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public CacheInfoMsg(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public String toString() {
        return "CacheInfoMsg{" +
                "cacheInfo=" + cacheInfo +
                '}';
    }
}
