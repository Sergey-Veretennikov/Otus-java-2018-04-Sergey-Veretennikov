package ru.otus.L071;

import ru.otus.L061.ATM;
import ru.otus.L071.mementos.AtmMemento;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {
    private List<ATM> atms = new ArrayList<>();
    private final AtmMemento atmMemento = new AtmMemento();

    @Override
    public void addATM(ATM atm) {
        if (!atms.contains(atm))
            atms.add(atm);
    }

    @Override
    public int infoBalance() {
        return atms.stream().mapToInt(ATM::infoBalance).sum();
    }

    @Override
    public int getATMCount() {
        return atms.size();
    }

    @Override
    public List<ATM> getATMs() {
        return atms;
    }

    @Override
    public ATM getATM(int nomer) {
        if (nomer < atms.size())
            return atms.get(nomer);
        else
            return null;
    }

    @Override
    public void restoreInitialState() {
        atms = atmMemento.getAtmMemento();
    }

    @Override
    public void restoreInitialState(ATM... atm) {
        for (int i = 0; i < atm.length; i++) {
            if (!atms.contains(atm[i])) break;
            for (ATM a : atmMemento.getAtmMemento()) {
                if (a.getNomer() == atm[i].getNomer()) atms.set(atms.indexOf(atm[i]), a);
            }
        }
    }

    @Override
    public AtmMemento getDepartmAtmMemento() {
        return atmMemento;
    }
}
