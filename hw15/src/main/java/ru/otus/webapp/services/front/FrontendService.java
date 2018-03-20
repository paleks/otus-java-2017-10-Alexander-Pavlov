package ru.otus.webapp.services.front;

import ru.otus.webapp.messagesystem.Addressee;
import ru.otus.webapp.services.cache.CacheInfo;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void handleRequest();

    void handleResponce(CacheInfo info) throws IOException;
}

