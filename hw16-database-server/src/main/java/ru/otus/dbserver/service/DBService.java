package ru.otus.dbserver.service;

import ru.otus.dbserver.cache.CacheInfo;
import ru.otus.dbserver.entity.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    <T extends DataSet> long save(T user) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    public CacheInfo getCacheInfo();
}
