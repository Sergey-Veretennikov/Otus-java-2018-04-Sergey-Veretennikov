package ru.otus.L101.dbservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.L101.base.AddressDataSet;
import ru.otus.L101.base.UserDataSet;
import ru.otus.L101.myORM.connection.ConnectionHelper;
import ru.otus.L101.myORM.connection.FabricConnection;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DbserviceImplTest {
    private Dbservice dbservice;
    private UserDataSet userDataSet;

    @Before
    public void create() throws SQLException {
        FabricConnection connection = new ConnectionHelper();
        dbservice = new DbserviceImpl(connection);
        dbservice.startup();
    }

    @After
    public void close() throws SQLException {
        dbservice.shutdown();
    }

    @Test
    public void save() throws SQLException {
        userDataSet = new UserDataSet("PKNBVCX", 155, new AddressDataSet("okmv",10));
        dbservice.save(userDataSet);

        assertEquals(userDataSet, dbservice.read(userDataSet.getId()));
    }

    @Test
    public void read() throws SQLException {
        userDataSet = dbservice.read(1);

        assertEquals(1,userDataSet.getId() );
    }
}