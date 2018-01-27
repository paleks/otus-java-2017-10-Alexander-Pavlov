package ru.otus.service;

import ru.otus.cache.CacheElement;
import ru.otus.cache.CacheEngine;
import ru.otus.config.Configuration;
import ru.otus.dao.DataSetDAO;
import ru.otus.entity.DataSet;
import ru.otus.util.DBHelper;

import javax.xml.crypto.Data;
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
    public <T extends DataSet> long save(T dataSet) throws SQLException {
        if (this.configuration.getClasses().contains(dataSet.getClass())) {
            long id = -1;
            try {
                DataSetDAO dao = (DataSetDAO) dataSet.getDataAccessObjectClass().newInstance();
                dao.setConnection(this.connection);
                id = dao.save(dataSet);
                addToCache(dataSet.getClass(), dataSet);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return id;
        } else {
            throw new UnsupportedOperationException("Class " + dataSet.getClass().getSimpleName() +
                " is not supported by this method");
        }
    }

    private void addToCache(Class clazz, DataSet dataSet) {
        CacheEngine<Long, DataSet> cacheEngine = this.configuration.getCacheEngine(clazz);
        if (cacheEngine != null) {
            CacheElement<Long, DataSet> element = new CacheElement(dataSet.getId(), dataSet);
            cacheEngine.put(element);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        if (this.configuration.getClasses().contains(clazz)) {
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
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return (T) dataSet;
        } else {
            throw new UnsupportedOperationException("Class " + clazz.getSimpleName() +
                    " is not supported by this method");
        }
    }

    private DataSet getFromCache(long id, Class clazz) {
        CacheEngine<Long, DataSet> cacheEngine = this.configuration.getCacheEngine(clazz);
        if (cacheEngine != null && cacheEngine.contains(id)) {
            return (DataSet) cacheEngine.get(id).getValue();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
