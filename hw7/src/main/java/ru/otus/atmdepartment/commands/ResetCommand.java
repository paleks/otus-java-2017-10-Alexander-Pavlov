package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.messages.Message;
import ru.otus.atmdepartment.messages.ResetMessage;

public class ResetCommand implements CommandWithResult {
    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public void execute(Message msg) {
        ResetMessage message = (ResetMessage) msg;
        message.getAtm().reset();
    }
}
