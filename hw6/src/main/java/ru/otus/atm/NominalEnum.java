package ru.otus.atm;

public enum NominalEnum {
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private int nominalVal;

    private NominalEnum(int nominal) {
        this.nominalVal = nominal;
    }

    public int getNominalVal() {
        return this.nominalVal;
    }

    @Override
    public String toString() {
        return "NominalEnum{" +
                "name=" + this.name() +
                ", nominalVal=" + nominalVal +
                '}';
    }
}
