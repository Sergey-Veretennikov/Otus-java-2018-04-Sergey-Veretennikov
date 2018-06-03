package ru.otus.L071;

import ru.otus.L061.ATM;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {
    private final List<ATM> atms;

    public DepartmentImpl() {
        this.atms = new ArrayList<>();
    }

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

}
