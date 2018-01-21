package ru.otus.util;

import org.hibernate.cfg.Configuration;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.UserDataSet;
import ru.otus.executor.Executor;
import ru.otus.service.DBService;
import ru.otus.service.DBServiceHibernateImpl;
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

    public static DBService getDBServiceHibernateInstance() {
        return new DBServiceHibernateImpl(getHibernateConfiguration());
    }

    public static Configuration getHibernateConfiguration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        //configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/otus_db");
        configuration.setProperty("hibernate.connection.username", "otus");
        configuration.setProperty("hibernate.connection.password", "otus");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        return configuration;
    }
}
