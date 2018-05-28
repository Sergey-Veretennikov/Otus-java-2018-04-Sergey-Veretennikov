package ru.otus.L061;

import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;

public class Main {
    public static void main(String[] args) throws InsufficientFundsException, InvalidAmountException {
        ATM atm = new ATM();

        atm.loadMoney(Denomination._50, 100);
        atm.loadMoney(Denomination._100, 100);
        atm.loadMoney(Denomination._200, 100);
        atm.loadMoney(Denomination._500, 100);
        atm.loadMoney(Denomination._1000, 1);
//        atm.loadMoney(Denomination._2000,100);
//        atm.loadMoney(Denomination._5000,100);

        System.out.println(atm.infoBalance());
        System.out.println(atm.giveMoney(4500));
        System.out.println(atm.infoBalance());
    }
}
