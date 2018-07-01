package ru.otus.L101.dbservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.L101.base.AddressDataSet;
import ru.otus.L101.base.PhoneDataSet;
import ru.otus.L101.base.UserDataSet;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DBServiceHibernateTest {
    private Dbservice dbservice;

    @Before
    public void startup() throws SQLException {
        dbservice = new DBServiceHibernate();
        dbservice.startup();
    }

    @Test
    public void getLocalStatus() throws SQLException {
        String status = dbservice.getLocalStatus();
        System.out.println("Status: " + status);

        assertEquals("ACTIVE", status);
    }

    @Test
    public void save() throws SQLException {
        UserDataSet user = new UserDataSet("LEO", 125,
                new AddressDataSet("Truda", 10), new PhoneDataSet("+1 234 567 8018"));
        dbservice.save(user);

        UserDataSet userFact = dbservice.readByName("LEO");

        assertEquals("Name", user.getName(), userFact.getName());
        assertEquals("Age", user.getAge(), userFact.getAge());
        assertEquals("Street", user.getAddressDataSet().getStreet(),
                userFact.getAddressDataSet().getStreet());
        assertEquals("Index", user.getAddressDataSet().getIndex(),
                userFact.getAddressDataSet().getIndex());
        assertEquals("Phone size", user.getPhoneDataSets().size(), userFact.getPhoneDataSets().size());
//        assertEquals("Phone",user.getPhoneDataSets(), userFact.getPhoneDataSets());
    }

    @Test
    public void read() throws SQLException {
        UserDataSet user = new UserDataSet("HENRY", 147,
                new AddressDataSet("Moskovskaya", 45), new PhoneDataSet("+7 987 645 4545"),
                new PhoneDataSet("+67 890 344 4422"));
        dbservice.save(user);

        UserDataSet userFact = dbservice.read(user.getId());

        assertEquals("id", user.getId(), userFact.getId());
    }

    @Test
    public void readByName() throws SQLException {
        UserDataSet user = new UserDataSet("DANIEL", 456,
                new AddressDataSet("Mira", 78), new PhoneDataSet("+67 344 4422"));
        dbservice.save(user);

        UserDataSet userFact = dbservice.readByName("DANIEL");

        assertEquals("Name", user.getName(), userFact.getName());

    }

    @Test
    public void readAll() throws SQLException {
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

        List<UserDataSet> userList = dbservice.readAll();
        assertEquals("userList size", 3, userList.size());
    }

    @After
    public void shutdown() throws SQLException {
        dbservice.shutdown();
    }
}