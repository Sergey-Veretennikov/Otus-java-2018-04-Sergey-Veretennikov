package ru.otus.L071;

import ru.otus.L061.ATM;
import ru.otus.L071.mementos.AtmMemento;

import java.util.List;

public interface Department {
    void addATM(ATM atm);

    int infoBalance();

    int getATMCount();

    List<ATM> getATMs();

    ATM getATM(int nomer);

    void restoreInitialState();

    AtmMemento getDepartmAtmMemento();
}
