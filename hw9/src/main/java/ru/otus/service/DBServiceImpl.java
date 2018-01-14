package ru.otus.service;

import ru.otus.entity.DataSet;
import ru.otus.entity.UserDataSet;
import ru.otus.executor.Executor;
import ru.otus.util.DBHelper;

import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private static final String INSERT_TEMPLATE = "INSERT INTO TABLE_USER(NAME,AGE) VALUES('%s',%s)";
    private static final String SELECT_TEMPLATE = "SELECT * FROM TABLE_USER WHERE ID=%s";
    private static final int ID_COLUMN   = 1;
    private static final int NAME_COLUMN = 2;
    private static final int AGE_COLUMN  = 3;

    @Override
    public <T extends DataSet> int save(T user) throws SQLException {
        if (user instanceof UserDataSet) {
            Executor executor = DBHelper.getExecutorInstance();
            String query = String.format(INSERT_TEMPLATE, ((UserDataSet) user).getName(), ((UserDataSet) user).getAge());
            int id = executor.update(query, genKeys -> {
                genKeys.next();
                return genKeys.getInt(ID_COLUMN);
            });
            return id;
        } else {
            throw new UnsupportedOperationException("At this moment class " + user.getClass().getSimpleName() +
                    "is not supported by this method");
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        if (clazz.getSimpleName().equals(UserDataSet.class.getSimpleName())) {
            Executor executor = DBHelper.getExecutorInstance();
            String query = String.format(SELECT_TEMPLATE, id);
            UserDataSet user = executor.execute(query, resultSet -> {
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
}
