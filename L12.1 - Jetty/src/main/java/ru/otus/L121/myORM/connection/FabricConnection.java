package ru.otus.L121.myORM.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface FabricConnection extends AutoCloseable {

    Connection getConnection();

    void close() throws SQLException;

    String getStatus() throws SQLException;
}
