package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

public class GetBalanceMessage extends ATMMessage {
    public GetBalanceMessage(ATM atm) {
        super(Type.BALANCE, atm);
    }
}
