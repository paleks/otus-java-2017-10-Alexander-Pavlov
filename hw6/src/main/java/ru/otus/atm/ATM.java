package ru.otus.atm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ATM {
    private List<Cell> cells = new ArrayList<Cell>();

    public ATM() {
        for (NominalEnum nominal : NominalEnum.values()) {
            cells.add(new Cell(nominal));
        }

        Collections.sort(cells, new Comparator<Cell>() {
            public int compare(Cell c1, Cell c2) {
                if (c1.getNominal().getNominalVal() < c2.getNominal().getNominalVal()) {
                    return 1;
                }
                if (c1.getNominal().getNominalVal() > c2.getNominal().getNominalVal()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public void add(NominalEnum nominal, int quantity) {
        Cell cell = cells.stream().filter(c -> c.getNominal().equals(nominal)).findFirst().get();
        cell.add(quantity);
    }

    public MoneyPack withdraw(int sumToWithdraw) {
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
        MoneyPack moneyPack = new MoneyPack();
        for (Cell cell : cells) {
            int quantityBefore = cell.getQuantity();
            sumToWithdraw = cell.withdraw(sumToWithdraw);
            int quantityAfter = cell.getQuantity();
            moneyPack.getMoneyPack().add(new NotePack(cell.getNominal(), quantityBefore - quantityAfter));
        }
        return moneyPack;
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
}
