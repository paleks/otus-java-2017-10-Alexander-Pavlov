package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

import java.util.List;

public class ResetAllMessage extends ATMDepartmentMessage {
    public ResetAllMessage(List<ATM> atms) {
        super(Type.RESET_ALL, atms);
    }
}
