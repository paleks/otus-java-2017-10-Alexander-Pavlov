package ru.otus.web.model.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    private Executor() {}

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T update(String query, Handler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            T result = handler.handle(stmt);
            stmt.close();
            return result;
        }
    }

    public <T> T execute(String query, Handler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            T result = handler.handle(stmt);
            stmt.close();
            return result;
        }
    }
}
