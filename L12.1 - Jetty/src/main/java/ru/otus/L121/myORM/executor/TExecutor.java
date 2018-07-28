package ru.otus.L121.myORM.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TExecutor extends LogExecutor {
    public TExecutor(Connection connection) {
        super(connection);
    }

    public <T> T execQuery(String query, long id, TResultHandler<T> handler) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return handler.handle(resultSet);
            }
        }
    }

    @FunctionalInterface
    public interface TResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }
}
