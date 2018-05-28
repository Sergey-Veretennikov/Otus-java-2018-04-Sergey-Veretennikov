package ru.otus.L061;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ATMTest {
    private ATM atm;

    @Before
    public void setUp() throws Exception {
        atm = new ATM();
    }

    @Test
    public void loadMoney() {
        atm.loadMoney(Denomination._5000, 5);
        Assert.assertEquals(25000, atm.infoBalance());

        atm.loadMoney(Denomination._500, 1);
        Assert.assertEquals(25500, atm.infoBalance());
    }

    @Test
    public void infoBalance() {
    }

    @Test
    public void giveMoney() {
    }
}