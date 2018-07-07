package ru.otus.L111.dbservice;

import ru.otus.L111.base.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface Dbservice {
    void startup() throws SQLException;

    String getLocalStatus() throws SQLException;

    void save(UserDataSet dataSet) throws SQLException;

    UserDataSet read(long id) throws SQLException;

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown() throws SQLException;
}

