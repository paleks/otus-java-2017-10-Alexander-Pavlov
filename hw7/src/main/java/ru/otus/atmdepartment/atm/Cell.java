package ru.otus.atmdepartment.atm;

public class Cell {
    private NominalEnum nominal;
    private int quantity;

    public Cell(NominalEnum nominal) {
        this.nominal = nominal;
    }

    public void add(int quantityToAdd) {
        this.quantity += quantityToAdd;
    }

    public int withdraw(int sumToWithdraw) {
        if (this.quantity > 0) {
            int notesToWithdraw = sumToWithdraw / this.nominal.getNominalVal();
            int remainder = 0;
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

    @Override
    public String toString() {
        return "Cell{" +
                "nominal=" + nominal +
                ", quantity=" + quantity +
                ", balance=" + getBalance() +
                '}';
    }
}
