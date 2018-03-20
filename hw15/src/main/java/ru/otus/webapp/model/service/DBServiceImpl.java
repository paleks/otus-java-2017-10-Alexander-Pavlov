package ru.otus.webapp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.webapp.services.cache.CacheElement;
import ru.otus.webapp.model.config.Configuration;
import ru.otus.webapp.model.dao.DataSetDAO;
import ru.otus.webapp.model.entity.DataSet;
import ru.otus.webapp.model.util.DBHelper;
import ru.otus.webapp.services.cache.CacheEngine;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    @Autowired
    private CacheEngine<String, ? extends DataSet> cacheEngine;

    private Connection connection;
    private Configuration configuration;

    public DBServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T extends DataSet> long save(T dataSet) throws SQLException {
        if (this.configuration.isClassAdded(dataSet.getClass())) {
            initConnection();
            long id = -1;
            try {
                DataSetDAO dao = (DataSetDAO) dataSet.getDataAccessObjectClass().newInstance();
                dao.setConnection(this.connection);
                id = dao.save(dataSet);
                addToCache(dataSet);
            } catch (InstantiationException | IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
            return id;
        } else {
            throw new UnsupportedOperationException("Class " + dataSet.getClass().getSimpleName() +
                " is not supported");
        }
    }

    private void initConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DBHelper.getConnection();
        }
    }

    private void addToCache(DataSet dataSet) {
        this.cacheEngine.put(
                new CacheElement(dataSet.getClass().getSimpleName() + dataSet.getId(), dataSet));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        if (this.configuration.isClassAdded(clazz)) {
            initConnection();
            DataSet dataSet = null;
            try {
                dataSet = getFromCache(id, clazz);
                if (dataSet != null) {
                    return (T) dataSet;
                }
                dataSet = clazz.newInstance();
                DataSetDAO dao = (DataSetDAO) dataSet.getDataAccessObjectClass().newInstance();
                dao.setConnection(this.connection);
                dataSet = dao.read(id);
                addToCache(dataSet);
            } catch (InstantiationException | IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }

            return (T) dataSet;
        } else {
            throw new UnsupportedOperationException("Class " + clazz.getSimpleName() +
                    " is not supported");
        }
    }

    private DataSet getFromCache(long id, Class clazz) {
        CacheElement<String, ? extends DataSet> element = this.cacheEngine.get(clazz.getSimpleName() + id);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            this.connection.close();
        }
    }
}
