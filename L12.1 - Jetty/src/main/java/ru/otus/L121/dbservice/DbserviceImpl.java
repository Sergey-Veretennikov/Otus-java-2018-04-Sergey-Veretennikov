package ru.otus.L121.dbservice;

import ru.otus.L121.base.UserDataSet;
import ru.otus.L121.cache.CacheElement;
import ru.otus.L121.cache.CacheEngine;
import ru.otus.L121.cache.CacheEngineImpl;
import ru.otus.L121.myORM.UsersDAO;
import ru.otus.L121.myORM.connection.FabricConnection;

import java.sql.SQLException;
import java.util.List;

public class DbserviceImpl implements Dbservice {
    private final FabricConnection connection;
    private UsersDAO usersDAO;
    private CacheEngine<Long, UserDataSet> userDStCache;

    public static final int MAX_ELEMENTS = 50;
    public static final long LIFE_TIMES_MS = 5000;
    public static final long IDLE_TIME_MS = 3000;

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
        userDStCache = new CacheEngineImpl<>(MAX_ELEMENTS, LIFE_TIMES_MS, IDLE_TIME_MS, false);
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
        CacheElement<UserDataSet> element = userDStCache.get(id);
        UserDataSet userDS = null;
        if (element != null) {
            userDS = element.getValue();
            System.out.println("get not from db, id:" + id);
        }
        if (userDS == null) {
            userDS = usersDAO.load(id, UserDataSet.class);
            userDStCache.put(id, new CacheElement<>(userDS));
            System.out.println("get from db, id:" + id);
        }
        return userDS;
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

    public CacheEngine<Long, UserDataSet> getUserDStCache() {
        return userDStCache;
    }
}
