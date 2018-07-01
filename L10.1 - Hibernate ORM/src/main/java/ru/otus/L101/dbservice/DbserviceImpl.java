package ru.otus.L101.dbservice;

import ru.otus.L101.base.UserDataSet;
import ru.otus.L101.myORM.UsersDAO;
import ru.otus.L101.myORM.connection.FabricConnection;

import java.sql.SQLException;
import java.util.List;

public class DbserviceImpl implements Dbservice {
    private final FabricConnection connection;
    private UsersDAO usersDAO;

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    public DbserviceImpl(FabricConnection connection) {
        this.connection = connection;
    }

    @Override
    public void startup() {
        usersDAO = new UsersDAO(connection.getConnection());
        System.out.println(usersDAO.getMetaData());
    }

    @Override
    public String getLocalStatus() throws SQLException {
        return connection.getStatus();
    }

    @Override
    public void save(UserDataSet dataSet) throws SQLException {
        usersDAO.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) throws SQLException {
        return usersDAO.load(id, UserDataSet.class);
    }

    @Override
    public UserDataSet readByName(String name) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return null;
    }

    @Override
    public void shutdown() throws SQLException {
        connection.close();
    }
}
