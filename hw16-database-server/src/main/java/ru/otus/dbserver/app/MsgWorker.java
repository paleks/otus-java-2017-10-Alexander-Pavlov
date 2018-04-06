package ru.otus.dbserver.app;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    Msg take() throws InterruptedException;

    void close() throws IOException;
}
