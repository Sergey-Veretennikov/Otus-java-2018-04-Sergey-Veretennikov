package ru.otus.L071.mementos;

import ru.otus.L061.ATM;

import java.util.ArrayList;
import java.util.List;

public class AtmMemento {
    private final List<ATM> atms = new ArrayList<>();

    public void addAtmMemento(ATM atm) {
        atms.add(atm);
    }

    public List<ATM> getAtmMemento() {
        return atms;
    }
}
