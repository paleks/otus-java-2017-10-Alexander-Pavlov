package ru.otus.webapp.messagesystem.msg;

import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.services.cache.CacheInfo;
import ru.otus.webapp.services.front.FrontendService;

import java.io.IOException;

public class MsgGetCacheAnswer extends MsgToFrontend {
    private final CacheInfo info;

    public MsgGetCacheAnswer(Address from, Address to, CacheInfo info) {
        super(from, to);
        this.info = info;
    }

    @Override
    public void exec(FrontendService frontendService) {
        try {
            frontendService.handleResponce(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
