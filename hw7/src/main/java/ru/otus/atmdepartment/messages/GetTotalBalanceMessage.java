package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

import java.util.List;

public class GetTotalBalanceMessage extends ATMDepartmentMessage {
    public GetTotalBalanceMessage(List<ATM> atms) {
        super(Type.TOTAL_BALANCE, atms);
    }
}
