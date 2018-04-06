package ru.otus.dbserver.dao;

import ru.otus.dbserver.entity.DataSet;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSetDAO {
    private Connection connection;

    public abstract long save(DataSet dataSet) throws SQLException;
    public abstract DataSet read(long id) throws SQLException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
