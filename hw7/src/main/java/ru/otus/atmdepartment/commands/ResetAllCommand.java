package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.atm.ATM;
import ru.otus.atmdepartment.messages.Message;
import ru.otus.atmdepartment.messages.ResetAllMessage;

public class ResetAllCommand implements CommandWithResult {
    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public void execute(Message msg) {
        for (ATM atm : ((ResetAllMessage) msg).getAtms()) {
            atm.reset();
        }
    }
}
