package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

public class ATMMessage extends Message {
    private ATM atm;

    public ATMMessage(Type type, ATM atm) {
        super(type);
        this.atm = atm;
    }

    public ATM getAtm() {
        return atm;
    }
}
