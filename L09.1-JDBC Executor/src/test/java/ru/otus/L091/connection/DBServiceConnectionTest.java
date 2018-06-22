package ru.otus.L091.connection;

import org.junit.*;
import ru.otus.L091.base.DBService;
import ru.otus.L091.base.UserDataSet;
import ru.otus.L091.executor.TExecutor;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBServiceConnectionTest {
    private DBService dbService;
    private UserDataSet userDataSet;

    @Before
    public void ConnectionHelper() throws SQLException {
        dbService = new DBServiceConnection();
        System.out.println(dbService.getMetaData());
        dbService.prepareTables();
    }

    @After
    public void Close() throws Exception {
        dbService.close();
    }

    @Test
    public void save() throws SQLException {
        userDataSet = new UserDataSet("QAZXSW", 159);
        dbService.save(userDataSet);

        assertEquals(userDataSet, dbService.load(userDataSet.getId(), UserDataSet.class));
    }

    @Test
    public void load() throws SQLException {
        dbService.save(new UserDataSet("ZXCDE", 147));
        userDataSet = dbService.load(1, UserDataSet.class);

        assertEquals(1, userDataSet.getId());
    }

    @Test(expected = SQLException.class)
    public void deleteTables() throws SQLException {
        dbService.deleteTables();
        TExecutor exec = new TExecutor(ConnectionHelper.getConnection());
        exec.execQuery("SELECT * FROM users", resultSet -> {
            return resultSet.next();
        });
    }
}