package ru.otus.L071;

import ru.otus.L061.ATM;

import java.util.List;

public interface Department {
    void addATM(ATM atm);

    int infoBalance();

    int getATMCount();

    List<ATM> getATMs();

    ATM getATM(int nomer);
}
