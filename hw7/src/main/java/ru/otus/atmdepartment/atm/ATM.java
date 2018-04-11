package ru.otus.atmdepartment.atm;

import ru.otus.atmdepartment.money.MoneyPack;
import ru.otus.atmdepartment.money.NominalEnum;

import java.util.EnumMap;

public interface ATM {
    public void takeIn(EnumMap<NominalEnum, Integer> nominalMap);
    public MoneyPack withdraw(int sumToWithdraw);
    public int getBalance();
    public void reset();
}
