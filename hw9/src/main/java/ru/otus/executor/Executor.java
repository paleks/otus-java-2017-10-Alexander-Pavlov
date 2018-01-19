package ru.otus.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    private Executor() {}

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
            T result = handler.handle(genKeys);
            stmt.close();
            return result;
        }
    }

    public <T> T execute(String query, Handler<T> handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet == null) {
                throw new RuntimeException();
            }
            T result = handler.handle(stmt.getResultSet());
            stmt.close();
            return result;
        }
    }
}
