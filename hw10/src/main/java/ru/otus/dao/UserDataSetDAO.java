package ru.otus.dao;

import org.hibernate.Session;
import ru.otus.entity.UserDataSet;

public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public long save(UserDataSet dataSet) {
        return (Long) session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }
}
