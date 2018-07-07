package ru.otus.L111.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.L111.base.AddressDataSet;
import ru.otus.L111.base.PhoneDataSet;
import ru.otus.L111.base.UserDataSet;
import ru.otus.L111.cache.CacheElement;
import ru.otus.L111.cache.CacheEngine;
import ru.otus.L111.cache.CacheEngineImpl;
import ru.otus.L111.hibernate.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernate implements Dbservice {
    private SessionFactory sessionFactory;
    private CacheEngine<Long, UserDataSet> userDStCache;

    public static final int MAX_ELEMENTS = 50;
    public static final long LIFE_TIMES_MS = 5000;
    public static final long IDLE_TIME_MS = 3000;

    @Override
    public void startup() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "1");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = crecreateSessionFactory(configuration);
        userDStCache = new CacheEngineImpl<>(MAX_ELEMENTS, LIFE_TIMES_MS, IDLE_TIME_MS, false);
    }

    private static SessionFactory crecreateSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(dataSet);
            transaction.commit();
        }
    }

    @Override
    public UserDataSet read(long id) {
        CacheElement<UserDataSet> element = userDStCache.get(id);
        UserDataSet userDS = null;
        if (element != null) {
            userDS = element.getValue();
            System.out.println("get not from db, id:" + id);
        }

        if (userDS == null) {
            userDS = readFromDb(id);
            userDStCache.put(id, new CacheElement<>(userDS));
        }
        return userDS;
    }

    private UserDataSet readFromDb(long id) {
        System.out.println("get from db, id:" + id);
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }
}
