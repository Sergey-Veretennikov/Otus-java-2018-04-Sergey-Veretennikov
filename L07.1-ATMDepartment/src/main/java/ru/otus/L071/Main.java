package ru.otus.L071;


import ru.otus.L061.ATM;
import ru.otus.L061.Denomination;
import ru.otus.L061.exception.InsufficientFundsException;
import ru.otus.L061.exception.InvalidAmountException;
import ru.otus.L071.builder.ATMBuilderImpl;

public class Main {
    public static void main(String[] args) throws InsufficientFundsException, InvalidAmountException {
        ATM atmWithoutDepartment = new ATMBuilderImpl()
                .withCell(Denomination.FiveHundred, 1)
                .withCell(Denomination.FiveThousand, 1)
                .withCell(Denomination.OneThousand, 1)
                .build();
        atmWithoutDepartment.giveMoney(500);

        System.out.println(atmWithoutDepartment.infoBalance());

        Department department = new DepartmentImpl();
        ATMBuilderImpl atmBuilder = new ATMBuilderImpl(department);
        department.addATM(atmBuilder.withCell(Denomination.Fifty, 1)
                .withCell(Denomination.Fifty, 1)
                .withCell(Denomination.OneThousand, 1)
                .build());
        department.addATM(atmBuilder.withCell(Denomination.Hundred, 1).
                withCell(Denomination.OneThousand, 1)
                .build());

        System.out.println(department.infoBalance());
        System.out.println(department.getATMs());

        ATM atm1 = department.getATM(0);
        ATM atm2 = department.getATM(1);

        atm1.giveMoney(100);
        atm2.giveMoney(100);

        System.out.println(department.infoBalance());
    }
}
