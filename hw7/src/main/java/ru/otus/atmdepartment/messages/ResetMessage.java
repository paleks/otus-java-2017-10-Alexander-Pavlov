package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

public class ResetMessage extends ATMMessage {
    public ResetMessage(ATM atm) {
        super(Type.RESET, atm);
    }
}
