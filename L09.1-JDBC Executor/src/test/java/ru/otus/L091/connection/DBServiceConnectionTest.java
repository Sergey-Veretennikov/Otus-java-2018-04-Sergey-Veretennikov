package ru.otus.L091.connection;

import org.junit.*;
import ru.otus.L091.base.DBService;
import ru.otus.L091.base.UserDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DBServiceConnectionTest {
    private DBService dbService;
    private UserDataSet userDataSet;

    @Before
    public void connectionHelper() throws SQLException {
        dbService = new DBServiceConnection();
        System.out.println(dbService.getMetaData());
        dbService.prepareTables();
    }

    @After
    public void close() throws Exception {
        dbService.close();
    }

    @Test
    public void prepareTables() throws SQLException {
        dbService.prepareTables();

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                    int in = rs.getMetaData().getColumnCount();
//                    assertEquals(3, in);
                    assertTrue(in != 0);
                }
            }
        }
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
    public void deleteTables() throws Exception {
        dbService.deleteTables();

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                    int in = rs.getMetaData().getColumnCount();
                }
            }
        }
    }
}