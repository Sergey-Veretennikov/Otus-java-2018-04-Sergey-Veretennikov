package ru.otus.L071.builder;

import ru.otus.L061.ATM;
import ru.otus.L061.Denomination;

public interface ATMBuilder {
    ATMBuilder withCell(Denomination nominal, int count);

    ATM build();
}
