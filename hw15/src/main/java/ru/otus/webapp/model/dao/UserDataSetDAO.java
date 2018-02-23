package ru.otus.webapp.model.dao;

import ru.otus.webapp.model.entity.DataSet;
import ru.otus.webapp.model.entity.UserDataSet;
import ru.otus.webapp.model.executor.Executor;
import ru.otus.webapp.model.util.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataSetDAO extends DataSetDAO {

    private static final String INSERT_TEMPLATE = "INSERT INTO TABLE_USER(NAME,AGE) VALUES(?,?)";
    private static final String SELECT_TEMPLATE = "SELECT * FROM TABLE_USER WHERE ID=?";
    private static final int ID_COLUMN   = 1;
    private static final int NAME_COLUMN = 2;
    private static final int AGE_COLUMN  = 3;

    @Override
    public long save(DataSet dataSet) throws SQLException {
        Executor executor = DBHelper.getExecutorInstance(this.getConnection());
        UserDataSet user = (UserDataSet) dataSet;
        long id = executor.update(INSERT_TEMPLATE, stmt -> {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.execute();
            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys == null) {
                throw new RuntimeException("GeneratedKeys is null!!!");
            }
            genKeys.next();
            return genKeys.getLong(ID_COLUMN);
        });
        dataSet.setId(id);
        return id;
    }

    @Override
    public DataSet read(long id) throws SQLException {
        Executor executor = DBHelper.getExecutorInstance(this.getConnection());
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
        return user;
    }
}
