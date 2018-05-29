package ru.otus.L061;

import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM {
    private int balance = 0;
    private Map<Denomination, Cell> cells = new HashMap<>();

    public void loadMoney(Denomination nominal, int count) {
        if (cells.containsKey(nominal)) {
            Cell c = cells.get(nominal);
            c.setCount(c.getCount() + count);
            cells.put(c.getNominal(), c);
            balance += c.getNominal().getValue() * count;
        } else {
            Cell cell = new Cell(nominal, count);
            cells.put(cell.getNominal(), cell);
            balance += cell.getNominal().getValue() * cell.getCount();
        }
    }

    public int infoBalance() {
        return balance;
    }

    public List<Integer> giveMoney(int amount) throws InsufficientFundsException, InvalidAmountException {

        if (amount > balance) throw new InsufficientFundsException("Недостаточно Средств");
        if (!checkPossibilityOfRelease(amount))
            throw new InvalidAmountException("Нет подходящего номинала для выдачи запрашиваемой суммы ");

        List<Integer> result = new ArrayList<>();
        balance -= amount;

        for (Denomination d : Denomination.values()) {
            if (cells.get(d) != null) {
                while (true) {
                    if ((amount - d.getValue()) < 0 || cells.get(d).getCount() == 0) break;
                    amount -= d.getValue();
                    Cell c = cells.get(d);
                    c.setCount(cells.get(d).getCount() - 1);
                    cells.put(d, c);
                    result.add(d.getValue());
                }
                if (amount == 0) break;
            }
        }
        return result;
    }

    private boolean checkPossibilityOfRelease(int amount) {
        Map<Denomination, Integer> valuesMap = new HashMap<>();
        for (Denomination d : Denomination.values()) {
            if (cells.get(d) != null) valuesMap.put(d, cells.get(d).getCount());
        }
        for (Denomination d : Denomination.values()) {
            if (valuesMap.get(d) != null) {
                while (true) {
                    if ((amount - d.getValue()) < 0 || valuesMap.get(d) == 0) break;
                    amount -= d.getValue();
                    valuesMap.put(d, valuesMap.get(d) - 1);
                }
                if (amount == 0) return true;
            }
        }
        return false;
    }
}