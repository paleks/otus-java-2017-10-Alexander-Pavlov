package ru.otus.atmdepartment.atm;

import ru.otus.atmdepartment.money.MoneyPack;
import ru.otus.atmdepartment.money.NominalEnum;

import java.util.*;

public class ATMImpl implements ATM {
    private List<Cell> cells = new ArrayList<>();
    private EnumMap<NominalEnum, Integer> initStateMap;

    public ATMImpl() {}

    private void add(NominalEnum nominal, int quantity) {
        Cell cell = cells.stream().filter(c -> c.getNominal().equals(nominal)).findFirst().get();
        cell.add(quantity);
    }

    @Override
    public void takeIn(EnumMap<NominalEnum, Integer> nominalMap) {
        this.initStateMap = nominalMap;

        NominalEnum[] nominal = NominalEnum.values();
        // init cells
        Cell cell = new Cell(nominal[nominal.length - 1]);
        cells.add(cell);
        for (int i = nominal.length - 2; i >= 0; i--) {
            Cell nextCell = new Cell(nominal[i]);
            cell.setNext(nextCell);
            cell = nextCell;
            cells.add(cell);
        }
        // fill cells
        for (Map.Entry<NominalEnum, Integer> nominalMapEntry : this.initStateMap.entrySet()) {
            this.add(nominalMapEntry.getKey(), nominalMapEntry.getValue());
        }
    }

    @Override
    public MoneyPack withdraw(int sumToWithdraw) {
        validate(sumToWithdraw);

        MoneyPack moneyPack = new MoneyPack();
        cells.get(0).withdraw(moneyPack, sumToWithdraw);

        return moneyPack;
    }

    private void validate(int sumToWithdraw) {
        if (sumToWithdraw <= 0) {
            throw new RuntimeException("The required amount should be > 0");
        }
        if (isATMEmpty()) {
            throw new RuntimeException("ATM is empty");
        }
        if (this.getBalance() < sumToWithdraw) {
            throw new RuntimeException("The required amount can not be withdrawn");
        }
        if (!isWithdrawalAvail(sumToWithdraw)) {
            throw new RuntimeException("The required amount can not be withdrawn with existing notes");
        }
    }

    private boolean isWithdrawalAvail(int sumToWithdraw) {
        for (Cell cell : cells) {
            if (cell.getNominal().getNominalVal() <= sumToWithdraw
                    && cell.getQuantity() > 0
                    && sumToWithdraw % cell.getNominal().getNominalVal() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isATMEmpty() {
        return this.getBalance() == 0;
    }

    @Override
    public int getBalance() {
        int account = 0;
        for (Cell cell : cells) {
            account += cell.getBalance();
        }
        return account;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "cells=" + cells +
                '}' +
                ", balance=" + this.getBalance();
    }

    public void reset() {
        this.cells.forEach(Cell::clear);
        // fill
        for (Map.Entry<NominalEnum, Integer> nominalMapEntry : this.initStateMap.entrySet()) {
            this.add(nominalMapEntry.getKey(), nominalMapEntry.getValue());
        }
    }

}