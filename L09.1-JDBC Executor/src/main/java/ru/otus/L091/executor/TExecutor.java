package ru.otus.L091.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TExecutor extends LogExecutor {

    private final Connection connection;

    public TExecutor(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet resultSet = stmt.getResultSet();
            return handler.handle(resultSet);
        }
    }

    @FunctionalInterface
    public interface TResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }
}
