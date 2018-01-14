package ru.otus.service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImplTest {
    @Test
    public void saveTest() throws SQLException {
        Connection connection = DBHelper.getConnection();
        System.out.println(
                "Connected to: " + connection.getMetaData().getURL() + "\n" +
                "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                "Driver: " + connection.getMetaData().getDriverName());

        DBService service = DBHelper.getDBServiceInstance();
        int id = service.save(new UserDataSet("Ivan", 2));
        System.out.println("New user with id=" + id + " was created");
        connection.close();
        System.out.println("Connection is closed...");
        Assert.assertTrue(id != -1);
    }

    @Test
    public void loadTest() throws SQLException {
        Connection connection = DBHelper.getConnection();
        System.out.println(
                "Connected to: " + connection.getMetaData().getURL() + "\n" +
                "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                "Driver: " + connection.getMetaData().getDriverName());

        DBService service = DBHelper.getDBServiceInstance();
        int id = service.save(new UserDataSet("Ivan", 2));
        System.out.println("New user with id=" + id + " was created");
        UserDataSet user = service.load(id, UserDataSet.class);
        System.out.println(user);
        connection.close();
        System.out.println("Connection is closed...");
        Assert.assertTrue(user.getId() == id);
    }
}