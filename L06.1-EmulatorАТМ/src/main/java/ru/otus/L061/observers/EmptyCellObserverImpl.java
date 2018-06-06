package ru.otus.L061.observers;

import ru.otus.L061.Cell;

public class EmptyCellObserverImpl implements EmptyCellObserver {
    private Cell cell;

    public EmptyCellObserverImpl(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void notifyCellIsEmpty() {
        System.out.println("Ячейка номиналом: " + cell.getNominal().getValue() + " - Пуста (" + cell.getCount() + ")");
    }

    @Override
    public Cell setEmptyCellObserver() {
        return cell;
    }
}
