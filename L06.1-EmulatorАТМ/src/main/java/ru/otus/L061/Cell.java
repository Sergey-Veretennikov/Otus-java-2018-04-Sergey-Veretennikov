package ru.otus.L061;

public class Cell {
    private final Denomination nominal;
    private int count;

    public Cell(Denomination nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    public Denomination getNominal() {
        return nominal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
