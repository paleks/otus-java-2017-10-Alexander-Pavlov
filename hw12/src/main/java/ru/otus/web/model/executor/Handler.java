package ru.otus.web.model.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface Handler<T> {
    T handle(PreparedStatement statement) throws SQLException;
}
