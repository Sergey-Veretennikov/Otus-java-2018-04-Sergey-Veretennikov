package ru.otus.L061;

import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;
import ru.otus.L061.observers.EmptyCellObserver;

import java.util.*;

public class ATM implements Cloneable {
    private Map<Denomination, Cell> cells = new EnumMap<>(Denomination.class);
    private final List<EmptyCellObserver> emptyCellObservers = new ArrayList<>();

    public void loadMoney(Denomination nominal, int count) {
        if (cells.containsKey(nominal)) {
            Cell c = cells.get(nominal);
            c.setCount(c.getCount() + count);
        } else {
            Cell cell = new Cell(nominal, count);
            cells.put(cell.getNominal(), cell);
        }
    }

    public int infoBalance() {
        int balance = 0;
        for (Denomination d : cells.keySet()) {
            balance += cells.get(d).getNominal().getValue() * cells.get(d).getCount();
        }
        return balance;
    }

    public List<Integer> giveMoney(int amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount > infoBalance()) throw new InsufficientFundsException("Недостаточно Средств: " + amount);
        if (!checkPossibilityOfRelease(amount))
            throw new InvalidAmountException("Нет подходящего номинала для выдачи запрашиваемой суммы: " + amount);

        List<Integer> result = new ArrayList<>();

        for (Denomination d : cells.keySet()) {
            while (true) {
                if ((amount - d.getValue()) < 0 || cells.get(d).getCount() == 0) break;
                amount -= d.getValue();
                Cell c = cells.get(d);
                c.setCount(cells.get(d).getCount() - 1);
                result.add(d.getValue());
            }
            if (amount == 0) break;
        }
        for (Denomination d : cells.keySet()) {
            if (cells.get(d).getCount() == 0) notifyEmptyCellObservers(cells.get(d).getNominal());
        }
        return result;
    }

    private boolean checkPossibilityOfRelease(int amount) {
        Map<Denomination, Integer> valuesMap = new EnumMap<>(Denomination.class);
        for (Denomination d : cells.keySet())
            valuesMap.put(d, cells.get(d).getCount());
        for (Denomination d : cells.keySet()) {
            while (true) {
                if ((amount - d.getValue()) < 0 || valuesMap.get(d) == 0) break;
                amount -= d.getValue();
                valuesMap.put(d, valuesMap.get(d) - 1);
            }
            if (amount == 0) return true;
        }
        return false;
    }

    public ATM clone() {
        ATM a = null;
        try {
            a = (ATM) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        a.cells = new EnumMap<>(Denomination.class);
        for (Denomination d : cells.keySet()) {
            Cell c = new Cell(d, cells.get(d).getCount());
            a.cells.put(d, c);
        }
        return a;
    }

    public void subscribeToEmptyCell(EmptyCellObserver observer) {
        if (!emptyCellObservers.contains(observer))
            emptyCellObservers.add(observer);
    }

    private void notifyEmptyCellObservers(Denomination denomination) {
        for (EmptyCellObserver ob : emptyCellObservers) {
            if (ob.setEmptyCellObserver().getNominal() == denomination)
                ob.notifyCellIsEmpty();
        }
    }
}