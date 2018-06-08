package ru.otus.L071;


import ru.otus.L061.ATM;
import ru.otus.L061.Denomination;
import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;
import ru.otus.L071.builder.ATMBuilder;
import ru.otus.L071.builder.ATMBuilderImpl;

public class Main {
    public static void main(String[] args) throws InsufficientFundsException, InvalidAmountException, IllegalAccessException {
        ATM atmWithoutDepartment = new ATMBuilderImpl()
                .withCell(Denomination.FiveHundred, 1)
                .withCell(Denomination.FiveThousand, 1)
                .withCell(Denomination.OneThousand, 1)
                .build();
        atmWithoutDepartment.giveMoney(500);
        System.out.println();
        Department department = new DepartmentImpl();
        ATMBuilder atmBuilder = new ATMBuilderImpl(department);
        department.addATM(atmBuilder.withCell(Denomination.Fifty, 1)
                .withCell(Denomination.Fifty, 1)
                .withCell(Denomination.OneThousand, 1)
                .build());
        department.addATM(atmBuilder.withCell(Denomination.Hundred, 1).
                withCell(Denomination.OneThousand, 1)
                .withCell(Denomination.TwoHundred, 10)
                .build());
        System.out.println("Начальный баланс АТМ без департамента: " + atmWithoutDepartment.infoBalance());
        System.out.println();
        System.out.println("Начальный баланс департамента: " + department.infoBalance());

        ATM atm1 = department.getATM(0);
        ATM atm2 = department.getATM(1);
        atm1.giveMoney(100);
        atm2.giveMoney(1000);
        System.out.println("Баланс департамента: " + department.infoBalance());

//        department.restoreInitialState();
        department.restoreInitialState(atm1,atm2);
        System.out.println("Начальное значение баланса департаминта: " + department.infoBalance());
    }
}
