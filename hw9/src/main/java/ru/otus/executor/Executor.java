package ru.otus.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T update(String query, Handler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys == null) {
                throw new RuntimeException();
            }
            return handler.handle(genKeys);
        }
    }

    public <T> T execute(String query, Handler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet == null) {
                throw new RuntimeException();
            }
            return handler.handle(stmt.getResultSet());
        }
    }
}
