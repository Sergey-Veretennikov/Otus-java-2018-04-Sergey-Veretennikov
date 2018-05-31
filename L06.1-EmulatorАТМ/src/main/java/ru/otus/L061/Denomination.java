package ru.otus.L061;

public enum Denomination {
    FiveThousand(5000),
    TwoThousand(2000),
    OneThousand(1000),
    FiveHundred(500),
    TwoHundred(200),
    Hundred(100),
    Fifty(50);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
