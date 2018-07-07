package ru.otus.L111.myORM.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LogExecutor {
    private final Connection connection;

    public LogExecutor(Connection connection) {
        this.connection = connection;
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    Connection getConnection() {
        return connection;
    }
}
