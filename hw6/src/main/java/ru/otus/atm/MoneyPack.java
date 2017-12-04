package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

public class MoneyPack {
    private List<NotePack> moneyPack = new ArrayList<NotePack>();

    public List<NotePack> getMoneyPack() {
        return moneyPack;
    }

    public void setMoneyPack(List<NotePack> moneyPack) {
        this.moneyPack = moneyPack;
    }

    public int getAmount() {
        int amount = 0;
        for (NotePack notePack : moneyPack) {
            amount += (notePack.getQuantity() * notePack.getNominal().getNominalVal());
        }
        return amount;
    }

    @Override
    public String toString() {
        return "MoneyPack{" +
                "moneyPack=" + this.moneyPack +
                ", amount=" + this.getAmount() +
                '}';
    }
}
