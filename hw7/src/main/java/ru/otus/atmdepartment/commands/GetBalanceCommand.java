package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.messages.GetBalanceMessage;
import ru.otus.atmdepartment.messages.Message;

public class GetBalanceCommand implements CommandWithResult {

    private Object result;

    @Override
    public Object getResult() {
        return this.result;
    }

    @Override
    public void execute(Message msg) {
        GetBalanceMessage message = (GetBalanceMessage) msg;
        this.result = message.getAtm().getBalance();
    }
}
