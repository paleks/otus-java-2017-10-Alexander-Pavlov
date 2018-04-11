package ru.otus.atmdepartment.messages;

import ru.otus.atmdepartment.atm.ATM;
import ru.otus.atmdepartment.money.NominalEnum;

import java.util.EnumMap;

public class TakeInMessage extends ATMMessage {

    private final EnumMap<NominalEnum, Integer> nominalMap;

    public EnumMap<NominalEnum, Integer> getNominalMap() {
        return nominalMap;
    }

    public TakeInMessage(EnumMap<NominalEnum, Integer> nominalMap, ATM atm) {
        super(Type.TAKE_IN, atm);
        this.nominalMap = nominalMap;
    }
}
