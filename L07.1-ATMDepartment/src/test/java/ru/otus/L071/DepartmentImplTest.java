package ru.otus.L071;

import org.junit.Before;
import org.junit.Test;
import ru.otus.L061.ATM;
import ru.otus.L061.Denomination;
import ru.otus.L071.builder.ATMBuilder;
import ru.otus.L071.builder.ATMBuilderImpl;

import static org.junit.Assert.*;

public class DepartmentImplTest {
    private Department department;
    private ATMBuilder atmBuilder;

    @Before
    public void createTest() {
        department = new DepartmentImpl();
        atmBuilder = new ATMBuilderImpl();

    }

    @Test
    public void create() {
        assertNotNull(department);
    }

    @Test
    public void addATM() {
        department.addATM(new ATM());
        department.addATM(new ATM());
        assertEquals(2, department.getATMCount());
    }

    @Test
    public void infoBalance() {
        ATM atm1 = atmBuilder.withCell(Denomination.OneThousand, 2)
                .withCell(Denomination.Fifty, 2)
                .withCell(Denomination.TwoThousand, 2)
                .build();
        ATM atm2 = atmBuilder.withCell(Denomination.Fifty, 1)
                .withCell(Denomination.Fifty, 1)
                .build();
        department.addATM(atm1);
        department.addATM(atm2);
        assertEquals(6200, department.infoBalance());

    }
}