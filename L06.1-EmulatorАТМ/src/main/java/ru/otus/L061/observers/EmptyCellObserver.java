package ru.otus.L061.observers;

import ru.otus.L061.Cell;

public interface EmptyCellObserver {
    void notifyCellIsEmpty();

    Cell setEmptyCellObserver();
}
