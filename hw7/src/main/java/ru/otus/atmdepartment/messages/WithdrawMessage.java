package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

public class WithdrawMessage extends ATMMessage {

    private int amount;

    public WithdrawMessage(ATM atm, int amount) {
        super(Type.WITHDRAW, atm);
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }
}
