package ru.otus.L061;

import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;

import java.util.*;

public class ATM {
    private Map<Denomination, Cell> cells = new EnumMap<>(Denomination.class);

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
}