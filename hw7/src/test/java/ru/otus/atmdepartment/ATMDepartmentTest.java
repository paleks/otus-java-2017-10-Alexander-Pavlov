package ru.otus.atmdepartment;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.atmdepartment.actor.Actor;
import ru.otus.atmdepartment.actor.ActorImpl;
import ru.otus.atmdepartment.atm.ATM;
import ru.otus.atmdepartment.atm.ATMFactory;
import ru.otus.atmdepartment.messages.*;
import ru.otus.atmdepartment.money.NominalEnum;

import java.util.EnumMap;

public class ATMDepartmentTest {

    @Test
    public void totalBalanceTest() {
        Actor actor = new ActorImpl();

        ATMDepartment atmDep = ATMDepartment.getInstance();

        EnumMap<NominalEnum, Integer> nominalMap = new EnumMap<>(NominalEnum.class);
        nominalMap.put(NominalEnum.FIVE_THOUSAND, 15);
        nominalMap.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap.put(NominalEnum.FIVE_HUNDRED, 10);
        nominalMap.put(NominalEnum.ONE_HUNDRED, 10);
        ATM atm1 = ATMFactory.getInstance();

        actor.act(new TakeInMessage(nominalMap, atm1));
        atmDep.addATM(atm1);

        nominalMap.clear();
        nominalMap.put(NominalEnum.FIVE_THOUSAND, 9);
        nominalMap.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap.put(NominalEnum.FIVE_HUNDRED, 8);
        nominalMap.put(NominalEnum.ONE_HUNDRED, 6);
        ATM atm2 = ATMFactory.getInstance();
        actor.act(new TakeInMessage(nominalMap, atm2));
        atmDep.addATM(atm2);

        Integer balance = (Integer) actor.act(new GetTotalBalanceMessage(atmDep.getAtmList()));
        Assert.assertTrue(atm1.getBalance() + atm2.getBalance() == balance.intValue());

        atmDep.removeATM(atm1);
        atmDep.removeATM(atm2);
    }

    @Test
    public void totalResetTest() {
        Actor actor = new ActorImpl();

        ATMDepartment atmDep = ATMDepartment.getInstance();
        EnumMap<NominalEnum, Integer> nominalMap1 = new EnumMap<>(NominalEnum.class);
        nominalMap1.put(NominalEnum.FIVE_THOUSAND, 15);
        nominalMap1.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap1.put(NominalEnum.FIVE_HUNDRED, 10);
        nominalMap1.put(NominalEnum.ONE_HUNDRED, 10);
        ATM atm1 = ATMFactory.getInstance();

        actor.act(new TakeInMessage(nominalMap1, atm1));

        atmDep.addATM(atm1);

        EnumMap<NominalEnum, Integer> nominalMap2 = new EnumMap<>(NominalEnum.class);
        nominalMap2.put(NominalEnum.FIVE_THOUSAND, 9);
        nominalMap2.put(NominalEnum.ONE_THOUSAND, 10);
        nominalMap2.put(NominalEnum.FIVE_HUNDRED, 8);
        nominalMap2.put(NominalEnum.ONE_HUNDRED, 6);
        ATM atm2 = ATMFactory.getInstance();

        actor.act(new TakeInMessage(nominalMap2, atm2));

        atmDep.addATM(atm2);

        int atm1InitBalance = (int) actor.act(new GetBalanceMessage(atm1));
        int atm2InitBalance = (int) actor.act(new GetBalanceMessage(atm2));

        Integer balance = (Integer) actor.act(new GetTotalBalanceMessage(atmDep.getAtmList()));

        Assert.assertTrue(atm1InitBalance + atm2InitBalance == balance);

        actor.act(new WithdrawMessage(atm1, 20000));
        actor.act(new WithdrawMessage(atm1, 10500));

        balance = (Integer) actor.act(new GetTotalBalanceMessage(atmDep.getAtmList()));
        Assert.assertTrue(((atm1InitBalance + atm2InitBalance) - 30500) == balance);

        actor.act(new ResetAllMessage(atmDep.getAtmList()));

        Integer balance1 = (Integer) actor.act(new GetBalanceMessage(atm1));
        Integer balance2 = (Integer) actor.act(new GetBalanceMessage(atm2));
        Assert.assertTrue(atm1InitBalance + atm2InitBalance == balance1.intValue() + balance2.intValue());

        atmDep.removeATM(atm1);
        atmDep.removeATM(atm2);
    }

}