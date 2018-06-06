package ru.otus.L071.builder;

import ru.otus.L061.ATM;
import ru.otus.L061.Cell;
import ru.otus.L061.Denomination;
import ru.otus.L061.observers.EmptyCellObserverImpl;
import ru.otus.L071.Department;
import ru.otus.L071.DepartmentImpl;

import java.util.EnumMap;
import java.util.Map;

public class ATMBuilderImpl implements ATMBuilder {
    private final Department department;
    private Map<Denomination, Integer> cells = new EnumMap<Denomination, Integer>(Denomination.class);


    public ATMBuilderImpl() {
        this.department = new DepartmentImpl();
    }

    public ATMBuilderImpl(Department department) {
        this.department = department;
    }

    @Override
    public ATMBuilder withCell(Denomination nominal, int count) {
        if (cells.containsKey(nominal)) {
            cells.put(nominal, cells.get(nominal) + count);
        } else
            cells.put(nominal, count);
        return this;
    }

    @Override
    public ATM build() {
        ATM atm = new ATM();
        for (Denomination d : cells.keySet()) {
            atm.loadMoney(d, cells.get(d));
            atm.subscribeToEmptyCell(new EmptyCellObserverImpl(new Cell(d, 0)));
        }
        department.getDepartmAtmMemento().addAtmMemento(atm.clone());
        cells.clear();
        return atm;
    }
}
