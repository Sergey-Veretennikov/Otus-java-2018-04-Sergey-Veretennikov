package ru.otus.L131.myORM.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper implements FabricConnection {
    private final Connection connection;

    public ConnectionHelper() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String url = "jdbc:postgresql://" +
                    "localhost:" +
                    "5432/" +
                    "postgres?" +
                    "user=postgres&" +
                    "password=1&";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    @Override
    public String getStatus() throws SQLException {
        return connection.isValid(1) ? "valid" : "inValid";
    }
}
