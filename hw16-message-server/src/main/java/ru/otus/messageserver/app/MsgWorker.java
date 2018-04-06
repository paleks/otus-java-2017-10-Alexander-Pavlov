package ru.otus.messageserver.app;

import ru.otus.messageserver.channel.Blocks;

import java.io.IOException;

public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException;
}
