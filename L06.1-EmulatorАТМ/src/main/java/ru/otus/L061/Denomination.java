package ru.otus.L061;

public enum Denomination {
    _5000 (5000),
    _2000 (2000),
    _1000 (1000),
    _500  (500),
    _200  (200),
    _100  (100),
    _50   (50);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
