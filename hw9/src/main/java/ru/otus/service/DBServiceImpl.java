package ru.otus.service;

import ru.otus.entity.DataSet;
import ru.otus.entity.UserDataSet;
import ru.otus.executor.Executor;
import ru.otus.util.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private Connection connection;

    private static final String INSERT_TEMPLATE = "INSERT INTO TABLE_USER(NAME,AGE) VALUES(?,?)";
    private static final String SELECT_TEMPLATE = "SELECT * FROM TABLE_USER WHERE ID=?";
    private static final int ID_COLUMN   = 1;
    private static final int NAME_COLUMN = 2;
    private static final int AGE_COLUMN  = 3;

    public DBServiceImpl() {
        this.connection = DBHelper.getConnection();
    }

    @Override
    public <T extends DataSet> int save(T user) throws SQLException {
        if (user instanceof UserDataSet) {
            Executor executor = DBHelper.getExecutorInstance(this.connection);
            return executor.update(INSERT_TEMPLATE, stmt -> {
                stmt.setString(1, ((UserDataSet) user).getName());
                stmt.setInt(2, ((UserDataSet) user).getAge());
                stmt.execute();
                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys == null) {
                    throw new RuntimeException("GeneratedKeys is null!!!");
                }
                genKeys.next();
                return genKeys.getInt(ID_COLUMN);
            });
        } else {
            throw new UnsupportedOperationException("At this moment class " + user.getClass().getSimpleName() +
                    "is not supported by this method");
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        if (clazz.getSimpleName().equals(UserDataSet.class.getSimpleName())) {
            Executor executor = DBHelper.getExecutorInstance(this.connection);
            UserDataSet user = executor.execute(SELECT_TEMPLATE, stmt -> {
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

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
