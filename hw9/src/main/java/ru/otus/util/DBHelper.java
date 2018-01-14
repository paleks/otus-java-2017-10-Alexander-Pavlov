package ru.otus.util;

import ru.otus.executor.Executor;
import ru.otus.service.DBService;
import ru.otus.service.DBServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection getConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/otus_db?useSSL=false";
        String login    = "otus";
        String password = "otus";
        try {
            Class.forName(driver);

            return DriverManager.getConnection(url, login, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Executor getExecutorInstance() {
        return new Executor(getConnection());
    }

    public static DBService getDBServiceInstance() {
        return new DBServiceImpl();
    }
}
