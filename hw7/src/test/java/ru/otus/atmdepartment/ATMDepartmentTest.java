package ru.otus.atmdepartment;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.atmdepartment.atm.ATM;
import ru.otus.atmdepartment.atm.ATMFactory;
import ru.otus.atmdepartment.atm.NominalEnum;

import java.util.EnumMap;

public class ATMDepartmentTest {

    @Test
    public void totalBalanceTest() {
        ATMDepartment atmDep = ATMDepartment.getInstance();
        EnumMap<NominalEnum, Integer> nominalMap = new EnumMap<NominalEnum, Integer>(NominalEnum.class);
        nominalMap.put(NominalEnum.FIVE_THOUSAND, 15);
        nominalMap.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap.put(NominalEnum.FIVE_HUNDRED, 10);
        nominalMap.put(NominalEnum.ONE_HUNDRED, 10);
        ATM atm1 = ATMFactory.getInstance(nominalMap);
        System.out.println(atm1);
        atmDep.addATM(atm1);

        nominalMap.clear();
        nominalMap.put(NominalEnum.FIVE_THOUSAND, 9);
        nominalMap.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap.put(NominalEnum.FIVE_HUNDRED, 8);
        nominalMap.put(NominalEnum.ONE_HUNDRED, 6);
        ATM atm2 = ATMFactory.getInstance(nominalMap);
        System.out.println(atm2);
        atmDep.addATM(atm2);

        System.out.println(atmDep.getTotalBalance());
        Assert.assertTrue(atm1.getBalance() + atm2.getBalance() == atmDep.getTotalBalance());

        atmDep.removeATM(atm1);
        atmDep.removeATM(atm2);
    }

    @Test
    public void totalResetTest() {
        ATMDepartment atmDep = ATMDepartment.getInstance();
        EnumMap<NominalEnum, Integer> nominalMap1 = new EnumMap<NominalEnum, Integer>(NominalEnum.class);
        nominalMap1.put(NominalEnum.FIVE_THOUSAND, 15);
        nominalMap1.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap1.put(NominalEnum.FIVE_HUNDRED, 10);
        nominalMap1.put(NominalEnum.ONE_HUNDRED, 10);
        ATM atm1 = ATMFactory.getInstance(nominalMap1);
        System.out.println(atm1);
        atmDep.addATM(atm1);

        EnumMap<NominalEnum, Integer> nominalMap2 = new EnumMap<NominalEnum, Integer>(NominalEnum.class);
        nominalMap2.put(NominalEnum.FIVE_THOUSAND, 9);
        nominalMap2.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap2.put(NominalEnum.FIVE_HUNDRED, 8);
        nominalMap2.put(NominalEnum.ONE_HUNDRED, 6);
        ATM atm2 = ATMFactory.getInstance(nominalMap2);
        System.out.println(atm2);
        atmDep.addATM(atm2);

        System.out.println(atmDep.getTotalBalance());

        int atm1InitBalance = atm1.getBalance();
        int atm2InitBalance = atm2.getBalance();

        Assert.assertTrue(atm1InitBalance + atm2InitBalance == atmDep.getTotalBalance());

        atm1.withdraw(20000);
        atm2.withdraw(10500);

        System.out.println(atmDep.getTotalBalance());
        Assert.assertTrue(((atm1InitBalance + atm2InitBalance) - 30500) == atmDep.getTotalBalance());

        atmDep.totalReset();

        System.out.println(atmDep.getTotalBalance());
        Assert.assertTrue(atm1InitBalance + atm2InitBalance == atm1.getBalance() + atm2.getBalance());

        atmDep.removeATM(atm1);
        atmDep.removeATM(atm2);
    }

}