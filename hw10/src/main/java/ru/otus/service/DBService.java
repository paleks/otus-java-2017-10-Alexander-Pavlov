package ru.otus.service;

import ru.otus.entity.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    <T extends DataSet> long save(T user) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
