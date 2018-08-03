package ru.otus.L131;

import ru.otus.L131.base.AddressDataSet;
import ru.otus.L131.base.PhoneDataSet;
import ru.otus.L131.base.UserDataSet;
import ru.otus.L131.dbservice.Dbservice;

import java.sql.SQLException;


public class LoadingDbservice {
    Dbservice dbservice;

    public LoadingDbservice(Dbservice dbservice) {
        this.dbservice = dbservice;
    }

    public void run() throws SQLException {
        String status = dbservice.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet user1 = new UserDataSet("LEO", 125,
                new AddressDataSet("Truda", 10), new PhoneDataSet("+1 234 567 8018"));
        UserDataSet user2 = new UserDataSet("HENRY", 147,
                new AddressDataSet("Moskovskaya", 45), new PhoneDataSet("+7 987 645 4545"),
                new PhoneDataSet("+67 890 344 4422"));
        UserDataSet user3 = new UserDataSet("DANIEL", 456,
                new AddressDataSet("Mira", 78), new PhoneDataSet("+67 344 4422"));

        dbservice.save(user1);
        dbservice.save(user2);
        dbservice.save(user3);

        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);
    }
}

