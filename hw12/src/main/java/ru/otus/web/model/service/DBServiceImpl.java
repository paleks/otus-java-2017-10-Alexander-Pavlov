package ru.otus.web.model.service;

import ru.otus.web.model.cache.CacheElement;
import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.config.Configuration;
import ru.otus.web.model.dao.DataSetDAO;
import ru.otus.web.model.entity.DataSet;
import ru.otus.web.model.entity.UserDataSet;
import ru.otus.web.model.util.DBHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private Connection connection;
    private Configuration configuration;

    private DBServiceImpl() {}

    public DBServiceImpl(Configuration configuration) {
        this.configuration = configuration;
        this.connection = DBHelper.getConnection();
    }

    @Override
    public <T extends DataSet> long save(T dataSet) {
        if (this.configuration.isClassAdded(dataSet.getClass())) {
            long id = -1;
            try {
                DataSetDAO dao = (DataSetDAO) dataSet.getDataAccessObjectClass().newInstance();
                dao.setConnection(this.connection);
                id = dao.save(dataSet);
                addToCache(UserDataSet.class, dataSet);
            } catch (InstantiationException | IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
            return id;
        } else {
            throw new UnsupportedOperationException("Class " + dataSet.getClass().getSimpleName() +
                " is not supported");
        }
    }

    private void addToCache(Class clazz, DataSet dataSet) {
        CacheEngine<Long, ? extends DataSet> cacheEngine = this.configuration.getCacheEngine(clazz);
        if (cacheEngine != null) {
            cacheEngine.put(new CacheElement(dataSet.getId(), dataSet));
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        if (this.configuration.isClassAdded(clazz)) {
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
        CacheEngine<Long, ? extends DataSet> cacheEngine = this.configuration.getCacheEngine(clazz);
        if (cacheEngine != null && cacheEngine.contains(id)) {
            return cacheEngine.get(id).getValue();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
