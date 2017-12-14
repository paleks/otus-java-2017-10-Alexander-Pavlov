package ru.otus.atmdepartment;

import java.util.ArrayList;
import java.util.List;

import ru.otus.atmdepartment.atm.ATM;

public class ATMDepartment {
    private static ATMDepartment instance;
    private List<ATM> atmList = new ArrayList<>();

    private ATMDepartment() {

    }

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

    public int getTotalBalance() {
        int totalBalance = 0;
        for (ATM atm : atmList) {
            totalBalance += atm.getBalance();
        }
        return totalBalance;
    }

    public void totalReset() {
        this.atmList.forEach(atm -> atm.reset());
    }
}
