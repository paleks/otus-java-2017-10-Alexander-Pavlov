package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;

import java.util.List;

public class ATMDepartmentMessage extends Message {
    private List<ATM> atms;

    public ATMDepartmentMessage(Type type, List<ATM> atms) {
        super(type);
        this.atms = atms;
    }

    public List<ATM> getAtms() {
        return this.atms;
    }
}
