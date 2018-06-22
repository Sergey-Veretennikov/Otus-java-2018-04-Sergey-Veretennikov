package ru.otus.L091.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

            String url = "jdbc:postgresql://" +
                    "localhost:" +
                    "5432/" +
                    "postgres?" +
                    "user=postgres&" +
                    "password=1&";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
