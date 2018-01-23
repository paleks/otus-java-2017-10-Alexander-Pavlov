package ru.otus.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.dao.UserDataSetDAO;
import ru.otus.entity.DataSet;
import ru.otus.entity.UserDataSet;

import java.sql.SQLException;

public class DBServiceHibernateImpl implements DBService {

    private SessionFactory sessionFactory;

    private DBServiceHibernateImpl() {}

    public DBServiceHibernateImpl(Configuration hibernateConfiguration) {
        this.sessionFactory = createSessionFactory(hibernateConfiguration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public <T extends DataSet> long save(T user) throws SQLException {
        try(Session session = sessionFactory.openSession()) {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return userDataSetDAO.save((UserDataSet) user);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        try(Session session = sessionFactory.openSession()) {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return (T) userDataSetDAO.read(id);
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
        System.out.println("Session factory is closed...");
    }
}
