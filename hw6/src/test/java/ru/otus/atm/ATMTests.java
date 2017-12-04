package ru.otus.atm;

import org.junit.Assert;
import org.junit.Test;

public class ATMTests {

    @Test
    public void testAllNominals() {
        System.out.println("=========>>>>> testAllNominals");

        ATM atm = new ATM();

        System.out.println("ATM is empty ...");
        System.out.println(atm);

        atm.add(NominalEnum.FIVE_THOUSAND, 15);
        atm.add(NominalEnum.ONE_THOUSAND, 10);
        atm.add(NominalEnum.FIVE_HUNDRED, 10);
        atm.add(NominalEnum.ONE_HUNDRED, 10);

        System.out.println("ATM is filled ...");
        System.out.println(atm);

        System.out.println("Withdrawing ...");
        MoneyPack money = atm.withdraw(65000);

        System.out.println("ATM after withdraw...");
        System.out.println(atm);

        System.out.println(money);

        Assert.assertTrue(atm.getBalance() == 26000 && money.getAmount() == 65000);
    }

    @Test
    public void testOneNominal() {
        System.out.println("=========>>>>> testOneNominal");
        ATM atm = new ATM();

        System.out.println("ATM is empty ...");
        System.out.println(atm);

        atm.add(NominalEnum.ONE_HUNDRED, 100);

        System.out.println("ATM is filled ...");
        System.out.println(atm);

        System.out.println("Withdrawing ...");
        MoneyPack money = atm.withdraw(6500);

        System.out.println("ATM after withdraw...");
        System.out.println(atm);

        System.out.println(money);

        Assert.assertTrue(atm.getBalance() == 3500 && money.getAmount() == 6500);
    }

    @Test(expected = RuntimeException.class)
    public void testNotCorrectInput() {
        System.out.println("=========>>>>> testNotCorrectInput");
        ATM atm = new ATM();

        System.out.println("ATM is empty ...");
        System.out.println(atm);

        atm.add(NominalEnum.ONE_HUNDRED, 100);

        System.out.println("ATM is filled ...");
        System.out.println(atm);

        System.out.println("Withdrawing ...");
        MoneyPack money = atm.withdraw(6550);
    }

}
