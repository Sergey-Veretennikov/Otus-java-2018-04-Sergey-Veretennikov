package ru.otus.L091.base;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void prepareTables() throws SQLException;

    void addUsersDataSet(DataSet... dataSets) throws SQLException;

    <T extends DataSet> void save(T user) throws SQLException;

    <T extends DataSet> T load(long Id, Class<T> clazz) throws SQLException;

    String getUserName(int id);

    void deleteTables() throws SQLException;
}
