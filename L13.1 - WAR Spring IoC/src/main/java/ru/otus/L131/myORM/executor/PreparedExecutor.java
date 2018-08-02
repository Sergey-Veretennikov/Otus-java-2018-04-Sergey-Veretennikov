package ru.otus.L131.myORM.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PreparedExecutor extends LogExecutor {
    public PreparedExecutor(Connection connection) {
        super(connection);
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try (PreparedStatement stmt = getConnection().prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            prepare.accept(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement preparedStatement) throws SQLException;
    }
}
