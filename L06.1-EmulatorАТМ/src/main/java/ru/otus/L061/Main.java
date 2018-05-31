package ru.otus.L061;

import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;

public class Main {
    public static void main(String[] args) throws InsufficientFundsException, InvalidAmountException {
        ATM atm = new ATM();

        atm.loadMoney(Denomination.Fifty, 100);
        atm.loadMoney(Denomination.Hundred, 100);
        atm.loadMoney(Denomination.TwoHundred, 100);
        atm.loadMoney(Denomination.FiveHundred, 100);
        atm.loadMoney(Denomination.FiveHundred, 15);
        atm.loadMoney(Denomination.TwoThousand, 100);
//        atm.loadMoney(Denomination.FiveThousand, 100);

        System.out.println(atm.infoBalance());
        System.out.println(atm.giveMoney(4500));
        System.out.println(atm.infoBalance());
    }
}
