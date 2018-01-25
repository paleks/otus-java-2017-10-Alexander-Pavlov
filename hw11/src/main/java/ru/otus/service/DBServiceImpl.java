package ru.otus.service;

import ru.otus.cache.CacheElement;
import ru.otus.cache.CacheEngine;
import ru.otus.entity.DataSet;
import ru.otus.entity.UserDataSet;
import ru.otus.executor.Executor;
import ru.otus.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private Connection connection;
    private final CacheEngine cacheEngine;

    private static final String INSERT_TEMPLATE = "INSERT INTO TABLE_USER(NAME,AGE) VALUES(?,?)";
    private static final String SELECT_TEMPLATE = "SELECT * FROM TABLE_USER WHERE ID=?";
    private static final int ID_COLUMN   = 1;
    private static final int NAME_COLUMN = 2;
    private static final int AGE_COLUMN  = 3;

    public DBServiceImpl() {
        this.connection = DBHelper.getConnection();
        this.cacheEngine = null;
    }

    public DBServiceImpl(CacheEngine cacheEngine) {
        this.connection = DBHelper.getConnection();
        this.cacheEngine = cacheEngine;
    }

    @Override
    public <T extends DataSet> long save(T user) throws SQLException {
        if (user instanceof UserDataSet) {
            Executor executor = DBHelper.getExecutorInstance(this.connection);
            long id = executor.update(INSERT_TEMPLATE, stmt -> {
                stmt.setString(1, ((UserDataSet) user).getName());
                stmt.setInt(2, ((UserDataSet) user).getAge());
                stmt.execute();
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys == null) {
                    throw new RuntimeException("GeneratedKeys is null!!!");
                }
                genKeys.next();
                return genKeys.getLong(ID_COLUMN);
            });
            user.setId(id);
            addToCache((UserDataSet) user, id);
            return id;
        } else {
            throw new UnsupportedOperationException("At this moment class " + user.getClass().getSimpleName() +
                    "is not supported by this method");
        }
    }

    private void addToCache(UserDataSet user, long id) {
        if (this.cacheEngine != null) {
            this.cacheEngine.put(new CacheElement(id, user));
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        if (clazz.getSimpleName().equals(UserDataSet.class.getSimpleName())) {
            UserDataSet user = getFromCache(id);
            if (user != null) {
                return (T) user;
            }
            Executor executor = DBHelper.getExecutorInstance(this.connection);
            user = executor.execute(SELECT_TEMPLATE, stmt -> {
                stmt.setLong(1, id);
                stmt.execute();
                ResultSet resultSet = stmt.getResultSet();
                if (resultSet == null) {
                    throw new RuntimeException("ResultSet is null!!!");
                }
                resultSet.next();
                return new UserDataSet(resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getInt(AGE_COLUMN));
            });
            return (T) user;
        } else {
            throw new UnsupportedOperationException("At this moment class " + clazz.getSimpleName() +
                    "is not supported by this method");
        }
    }

    private UserDataSet getFromCache(long id) {
        if (this.cacheEngine != null && this.cacheEngine.contains(id)) {
            return (UserDataSet) this.cacheEngine.get(id).getValue();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    public CacheEngine getCacheEngine() {
        return cacheEngine;
    }
}
