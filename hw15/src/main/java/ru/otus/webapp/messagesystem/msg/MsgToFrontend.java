package ru.otus.webapp.messagesystem.msg;

import ru.otus.webapp.messagesystem.Address;
import ru.otus.webapp.messagesystem.Addressee;
import ru.otus.webapp.messagesystem.Message;
import ru.otus.webapp.services.front.FrontendService;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}