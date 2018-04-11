package ru.otus.atmdepartment;

import ru.otus.atmdepartment.atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private static ATMDepartment instance;
    private List<ATM> atmList = new ArrayList<>();

    private ATMDepartment() {}

    public static ATMDepartment getInstance() {
        if (instance == null) {
            instance = new ATMDepartment();
        }
        return instance;
    }

    public void addATM(ATM atm) {
        this.atmList.add(atm);
    }

    public void removeATM(ATM atm) {
        this.atmList.remove(atm);
    }

    public List<ATM> getAtmList() {
        return this.atmList;
    }
}
