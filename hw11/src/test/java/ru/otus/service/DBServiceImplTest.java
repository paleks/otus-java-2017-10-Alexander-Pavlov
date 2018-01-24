package ru.otus.service;

import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

public class DBServiceImplTest {
    //@Test
    public void saveTestJDBC() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
    //        Assert.assertTrue(id != -1);
        }
    }

    //@Test
    public void loadTestJDBC() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
    //        Assert.assertTrue(user.getId() == id);
        }
    }
}