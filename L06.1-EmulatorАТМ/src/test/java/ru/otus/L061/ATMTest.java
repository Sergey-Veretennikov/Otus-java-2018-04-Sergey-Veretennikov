package ru.otus.L061;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;

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

        atm.loadMoney(Denomination._500, 1);
        Assert.assertEquals(26000, atm.infoBalance());
    }

    @Test
    public void giveMoney() throws InsufficientFundsException, InvalidAmountException {
        atm.loadMoney(Denomination._5000, 5);
        atm.loadMoney(Denomination._500, 1);
        atm.loadMoney(Denomination._500, 10);
        atm.loadMoney(Denomination._200, 10);

        atm.giveMoney(500);
        Assert.assertEquals(32000, atm.infoBalance());

        Assert.assertEquals(2, atm.giveMoney(700).size());
        Assert.assertEquals(3, atm.giveMoney(1200).size());
    }

    @Test(expected = InsufficientFundsException.class)
    public void InsufficientFundsException() throws InsufficientFundsException, InvalidAmountException {
        atm.giveMoney(100);
    }

    @Test(expected = InvalidAmountException.class)
    public void InvalidAmountException() throws InsufficientFundsException, InvalidAmountException {
        atm.loadMoney(Denomination._500,1);
        atm.giveMoney(100);
    }
}