package ru.otus.L131;

import ru.otus.L131.base.AddressDataSet;
import ru.otus.L131.base.PhoneDataSet;
import ru.otus.L131.base.UserDataSet;
import ru.otus.L131.cache.CacheEngine;
import ru.otus.L131.dbservice.DBServiceHibernate;
import ru.otus.L131.dbservice.Dbservice;

import java.sql.SQLException;


public class LoadingDbservice {
    CacheEngine<Long, UserDataSet> userDStCache;

    public LoadingDbservice(CacheEngine<Long, UserDataSet> userDStCache) throws SQLException {
        this.userDStCache = userDStCache;
        run();
    }

    public void run() throws SQLException {
        Dbservice dbservice = new DBServiceHibernate(userDStCache);
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

        dbservice.save(user1);
        dbservice.save(user2);
        dbservice.save(user3);

        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);
    }
}

