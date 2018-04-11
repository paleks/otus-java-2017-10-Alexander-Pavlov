package ru.otus.atmdepartment.atm;

import ru.otus.atmdepartment.money.MoneyPack;
import ru.otus.atmdepartment.money.NominalEnum;
import ru.otus.atmdepartment.money.NotePack;

public class Cell {
    private NominalEnum nominal;
    private int quantity;

    private Cell next;

    public Cell(NominalEnum nominal) {
        this.nominal = nominal;
    }

    public void add(int quantityToAdd) {
        this.quantity += quantityToAdd;
    }

    private int withdraw(int sumToWithdraw) {
        if (this.quantity > 0) {
            int notesToWithdraw = sumToWithdraw / this.nominal.getNominalVal();
            int remainder;
            if (notesToWithdraw > this.quantity) {
                remainder = sumToWithdraw - (this.nominal.getNominalVal() * this.quantity);
                this.quantity = 0;
            } else {
                remainder = sumToWithdraw % this.nominal.getNominalVal();
                this.quantity -= notesToWithdraw;
            }
            return remainder;
        }
        return sumToWithdraw;
    }

    public void withdraw(MoneyPack moneyPack, int sumToWithdraw) {
        int quantityBefore = this.quantity;
        sumToWithdraw = this.withdraw(sumToWithdraw);
        int quantityAfter = this.getQuantity();
        moneyPack.getMoneyPack().add(new NotePack(this.nominal, quantityBefore - quantityAfter));

        if (this.getNext() != null && sumToWithdraw > 0) {
            this.getNext().withdraw(moneyPack, sumToWithdraw);
        }
    }

    public NominalEnum getNominal() {
        return nominal;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBalance() {
        return this.quantity * this.nominal.getNominalVal();
    }

    public void clear() {
        this.quantity = 0;
    }

    public Cell getNext() {
        return next;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "nominal=" + nominal +
                ", quantity=" + quantity +
                ", balance=" + getBalance() +
                '}';
    }
}
