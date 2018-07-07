package ru.otus.L111;

import ru.otus.L111.base.AddressDataSet;
import ru.otus.L111.base.PhoneDataSet;
import ru.otus.L111.base.UserDataSet;
import ru.otus.L111.dbservice.DBServiceHibernate;
import ru.otus.L111.dbservice.Dbservice;
import ru.otus.L111.dbservice.DbserviceImpl;
import ru.otus.L111.myORM.connection.ConnectionHelper;
import ru.otus.L111.myORM.connection.FabricConnection;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
//        myORM();
        run();
    }

    public static void myORM() throws SQLException {
        try (FabricConnection connection = new ConnectionHelper()) {
            Dbservice dbservice = new DbserviceImpl(connection);
            dbservice.startup();
            ((DbserviceImpl) dbservice).getUsersDAO().prepareTables();
            dbservice.save(new UserDataSet("qwerrt", 10, new AddressDataSet("okmv", 10)));
            System.out.println(dbservice.read(1));
            System.out.println(dbservice.getLocalStatus());
            System.out.println(dbservice.read(1));
        }
    }

    public static void run() throws SQLException {
        Dbservice dbservice = new DBServiceHibernate();
        dbservice.startup();
        String status = dbservice.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet user1 = new UserDataSet("LEO", 125,
                new AddressDataSet("Truda", 10), new PhoneDataSet("+1 234 567 8018"));
        UserDataSet user2 = new UserDataSet("HENRY", 147,
                new AddressDataSet("Moskovskaya", 45), new PhoneDataSet("+7 987 645 4545"),
                new PhoneDataSet("+67 890 344 4422"));
        UserDataSet user3 = new UserDataSet("DANIEL", 456,
                new AddressDataSet("Mira", 78), new PhoneDataSet("+67 344 4422"));

        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        dbservice.save(user1);
        dbservice.save(user2);
        dbservice.save(user3);

        System.out.println("____________________________");

        System.out.println(dbservice.read(2));
        System.out.println(dbservice.read(2));
//        System.out.println(dbservice.readByName("DANIEL"));
//
//        List<UserDataSet> dataSets = dbservice.readAll();
//        for (UserDataSet userDataSet : dataSets) {
//            System.out.println(userDataSet);
//        }

        dbservice.shutdown();
    }
}
