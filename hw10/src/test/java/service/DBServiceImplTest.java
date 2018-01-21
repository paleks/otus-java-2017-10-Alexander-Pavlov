package service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.entity.AddressDataSet;
import ru.otus.service.DBService;
import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

public class DBServiceImplTest {
    @Test
    public void saveTest() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            Assert.assertTrue(id != -1);
        }
    }

    @Test
    public void loadTest() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }

    @Test
    public void saveTestHiber() throws Exception {
        try (DBService service = DBHelper.getDBServiceHibernateInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2, new AddressDataSet("Gorodeckaya")));
            System.out.println("New user with id=" + id + " was created");
            Assert.assertTrue(id != -1);
        }
    }

    @Test
    public void loadTestHiber() throws Exception {
        try (DBService service = DBHelper.getDBServiceHibernateInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2, new AddressDataSet("Gorodeckaya")));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }
}