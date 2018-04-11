package ru.otus.atmdepartment.atm;

public class ATMFactory {
    public static ATM getInstance() {
        return new ATMImpl();
    }
}
