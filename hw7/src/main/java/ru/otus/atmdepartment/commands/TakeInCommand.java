package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.messages.Message;
import ru.otus.atmdepartment.messages.TakeInMessage;

public class TakeInCommand implements CommandWithResult {

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public void execute(Message msg) {
        TakeInMessage message = (TakeInMessage) msg;
        message.getAtm().takeIn(message.getNominalMap());
    }
}
