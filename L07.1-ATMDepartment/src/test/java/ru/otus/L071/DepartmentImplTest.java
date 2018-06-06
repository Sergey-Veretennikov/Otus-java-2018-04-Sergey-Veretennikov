package ru.otus.L071;

import org.junit.Before;
import org.junit.Test;
import ru.otus.L061.ATM;
import ru.otus.L061.Cell;
import ru.otus.L061.Denomination;
import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;
import ru.otus.L061.observers.EmptyCellObserver;
import ru.otus.L071.builder.ATMBuilder;
import ru.otus.L071.builder.ATMBuilderImpl;

import static org.junit.Assert.*;

public class DepartmentImplTest {
    private Department department;
    private ATMBuilder atmBuilder;
    private ATMBuilder atmBuilder2;

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
    public void infoBalance() throws IllegalAccessException {
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

    @Test
    public void giveMoneyDepartment() throws InsufficientFundsException, InvalidAmountException, IllegalAccessException {
        atmBuilder2 = new ATMBuilderImpl(department);
        department.addATM(atmBuilder2.withCell(Denomination.OneThousand, 2)
                .withCell(Denomination.Fifty, 2)
                .withCell(Denomination.TwoThousand, 2)
                .build());
        department.addATM(atmBuilder2.withCell(Denomination.Fifty, 1)
                .withCell(Denomination.Fifty, 1)
                .build());
        ATM atm1 = department.getATM(0);
        ATM atm2 = department.getATM(1);
        atm1.giveMoney(1000);
        atm2.giveMoney(50);
        assertEquals(5150, department.infoBalance());
    }

    @Test
    public void restoreInitialState() throws InsufficientFundsException, InvalidAmountException, IllegalAccessException {
        atmBuilder2 = new ATMBuilderImpl(department);
        department.addATM(atmBuilder2.withCell(Denomination.OneThousand, 2)
                .withCell(Denomination.Fifty, 2)
                .withCell(Denomination.TwoThousand, 2)
                .build());
        department.addATM(atmBuilder2.withCell(Denomination.Fifty, 1)
                .withCell(Denomination.Fifty, 1)
                .build());
        int balance = department.infoBalance();

        ATM atm1 = department.getATM(0);
        ATM atm2 = department.getATM(1);
        atm1.giveMoney(1000);
        atm2.giveMoney(50);
        department.restoreInitialState();

        assertEquals(balance, department.infoBalance());
    }

    @Test
    public void notifyEmptyCellObservers() throws InsufficientFundsException, InvalidAmountException {
        final boolean[] notified = {false};

        Cell cell = new Cell(Denomination.Hundred, 2);
        ATM atm = new ATM();
        atm.loadMoney(cell.getNominal(), cell.getCount());
        atm.subscribeToEmptyCell(new EmptyCellObserver() {
            @Override
            public void notifyCellIsEmpty() {
                notified[0] = true;
            }

            @Override
            public Cell setEmptyCellObserver() {
                return cell;
            }

        });

        atm.giveMoney(200);
        assertTrue(notified[0]);

    }
}