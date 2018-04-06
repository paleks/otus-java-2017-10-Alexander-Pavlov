package ru.otus.messageserver.messagesystem.msg;

import ru.otus.messageserver.channel.FrontSocketMsgWorker;
import ru.otus.messageserver.messagesystem.Address;
import ru.otus.messageserver.messagesystem.Addressee;
import ru.otus.messageserver.messagesystem.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontSocketMsgWorker) {
            exec((FrontSocketMsgWorker) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontSocketMsgWorker frontendSocketWorker);
}