package ru.otus.atm;

public class NotePack {

    private NominalEnum nominal;
    private int quantity;

    public NotePack(NominalEnum nominal, int quantity) {
        this.nominal = nominal;
        this.quantity = quantity;
    }

    public NominalEnum getNominal() {
        return nominal;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "NotePack{" +
                "nominal=" + nominal +
                ", quantity=" + quantity +
                '}';
    }
}
