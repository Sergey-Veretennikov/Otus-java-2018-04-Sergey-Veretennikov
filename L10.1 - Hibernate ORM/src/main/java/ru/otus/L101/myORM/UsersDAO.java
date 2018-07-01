package ru.otus.L101.myORM;

import ru.otus.L101.base.DataSet;
import ru.otus.L101.myORM.executor.LogExecutor;
import ru.otus.L101.myORM.executor.PreparedExecutor;
import ru.otus.L101.myORM.executor.TExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    private final Connection connection;
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS Users" +
            "(ID SERIAL PRIMARY KEY," +
            " Name VARCHAR(255) NOT NULL," +
            " Age INT)";
    private static final String INSERT_USER = "insert into users (Name, Age) values (?,?)";
    private static final String DELETE_USER = "drop table users";
    private static final String SELECT_USER = "select id, name, age from users where id=?";

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public String getMetaData() {
        try {
            return "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void prepareTables() throws SQLException {
        LogExecutor logExecutor = new LogExecutor(connection);
        logExecutor.execUpdate(CREATE_TABLE_USER);
        connection.commit();
        System.out.println("Table created");
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        try {
            PreparedExecutor exec = new PreparedExecutor(connection);
            exec.execUpdate(INSERT_USER, preparedStatement -> {
                preparedStatement.setString(1,
                        (String) ReflectionHelper.getFieldValue(user, "name"));
                preparedStatement.setInt(2, (Integer) ReflectionHelper.getFieldValue(user, "age"));
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                ReflectionHelper.setSuperFieldValue(user, "id", rs.getInt(1));
            });
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {

        TExecutor tExecutor = new TExecutor(connection);
        return tExecutor.execQuery(SELECT_USER, id, resultSet -> {
            T t = (T) ReflectionHelper.createInstance(clazz.getName());
            if (resultSet.next()) {
                do {
                    ReflectionHelper.setSuperFieldValue(t, "id", resultSet.getInt("id"));
                    ReflectionHelper.setFieldValue(t, "name", resultSet.getString("name"));
                    ReflectionHelper.setFieldValue(t, "age", resultSet.getInt("age"));
                } while (resultSet.next());
            }
            return t;
        });
    }

    public void deleteTables() throws SQLException {
        LogExecutor logExecutor = new LogExecutor(connection);
        logExecutor.execUpdate(DELETE_USER);
        connection.commit();
    }
}
