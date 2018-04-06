package ru.otus.messageserver.messagesystem.msg;

import ru.otus.messageserver.app.CacheInfo;
import ru.otus.messageserver.channel.FrontSocketMsgWorker;
import ru.otus.messageserver.messages.CacheInfoMsg;
import ru.otus.messageserver.messagesystem.Address;

public class MsgGetCacheAnswer extends MsgToFrontend {
    private final CacheInfo info;

    public MsgGetCacheAnswer(Address from, Address to, CacheInfo info) {
        super(from, to);
        this.info = info;
    }

    @Override
    public void exec(FrontSocketMsgWorker frontendService) {
        frontendService.send(new CacheInfoMsg(info));
    }
}
