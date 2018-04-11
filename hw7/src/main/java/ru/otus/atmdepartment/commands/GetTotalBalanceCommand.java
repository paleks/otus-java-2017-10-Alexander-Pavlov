package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.atm.ATM;
import ru.otus.atmdepartment.messages.GetTotalBalanceMessage;
import ru.otus.atmdepartment.messages.Message;

public class GetTotalBalanceCommand implements CommandWithResult {
    private Object result;

    @Override
    public Object getResult() {
        return this.result;
    }

    @Override
    public void execute(Message msg) {
        int balance = 0;
        for (ATM atm : ((GetTotalBalanceMessage) msg).getAtms()) {
            balance += atm.getBalance();
        }
        this.result = balance;
    }
}
