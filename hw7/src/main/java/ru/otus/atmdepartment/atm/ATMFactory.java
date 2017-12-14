package ru.otus.atmdepartment.atm;

import java.util.EnumMap;
import java.util.Map;

public class ATMFactory {
    public static ATM getInstance(EnumMap<NominalEnum, Integer> nominalMap) {
        return new ATM(nominalMap);
    }
}
