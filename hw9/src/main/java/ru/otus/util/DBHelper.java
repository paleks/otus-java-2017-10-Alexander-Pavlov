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
            Connection connection = DriverManager.getConnection(url, login, password);
            System.out.println(
                    "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName());
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Executor getExecutorInstance(Connection connection) {
        return new Executor(connection);
    }

    public static DBService getDBServiceInstance() {
        return new DBServiceImpl();
    }
}
