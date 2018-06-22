package ru.otus.L091.connection;

import ru.otus.L091.ReflectionHelper;
import ru.otus.L091.base.DBService;
import ru.otus.L091.base.DataSet;
import ru.otus.L091.executor.LogExecutor;
import ru.otus.L091.executor.PreparedExecutor;
import ru.otus.L091.executor.TExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBServiceConnection implements DBService {

    private final Connection connection;
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS Users" +
            "(ID SERIAL PRIMARY KEY," +
            " Name VARCHAR(255) NOT NULL," +
            " Age INT)";
    private static final String INSERT_USER = "insert into users (Name, Age) values (?,?)";
    private static final String DELETE_USER = "drop table users";
    private static final String SELECT_USER = "select id, name, age from users where id=%s";

    public DBServiceConnection() {
        this.connection = ConnectionHelper.getConnection();
    }

    @Override
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

    @Override
    public void prepareTables() throws SQLException {
        LogExecutor logExecutor = new LogExecutor(connection);
        logExecutor.execUpdate(CREATE_TABLE_USER);
        System.out.println("Table created");
    }

    @Override
    public void addUsersDataSet(DataSet... dataSets) throws SQLException {
        try {
            PreparedExecutor exec = new PreparedExecutor(connection);
            connection.setAutoCommit(false);
            exec.execUpdate(INSERT_USER, preparedStatement -> {
                for (DataSet dataSet : dataSets) {
                    preparedStatement.setString(1,
                            (String) ReflectionHelper.getFieldValue(dataSet, "name"));
                    preparedStatement.setInt(2,
                            (Integer) ReflectionHelper.getFieldValue(dataSet, "age"));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            });
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) throws SQLException {
        try {
            PreparedExecutor exec = new PreparedExecutor(connection);
            connection.setAutoCommit(false);
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

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        TExecutor tExecutor = new TExecutor(connection);

        return tExecutor.execQuery(String.format(SELECT_USER, id), resultSet -> {
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

    @Override
    public String getUserName(int id) {
        return null;
    }

    @Override
    public void deleteTables() throws SQLException {
        LogExecutor logExecutor = new LogExecutor(connection);
        logExecutor.execUpdate(DELETE_USER);
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }
}
