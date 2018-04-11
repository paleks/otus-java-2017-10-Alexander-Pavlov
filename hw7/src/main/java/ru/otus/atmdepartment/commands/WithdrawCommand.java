package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.money.MoneyPack;
import ru.otus.atmdepartment.messages.Message;
import ru.otus.atmdepartment.messages.WithdrawMessage;

public class WithdrawCommand implements CommandWithResult {

    private MoneyPack money;

    @Override
    public Object getResult() {
        return this.money;
    }

    @Override
    public void execute(Message msg) {
        WithdrawMessage message = (WithdrawMessage) msg;
        this.money = message.getAtm().withdraw(message.getAmount());
    }
}
