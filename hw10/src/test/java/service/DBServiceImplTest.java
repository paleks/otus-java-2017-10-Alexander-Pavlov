package service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.service.DBService;
import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

import java.util.Arrays;

public class DBServiceImplTest {
    @Test
    public void saveTestJDBC() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            Assert.assertTrue(id != -1);
        }
    }

    @Test
    public void loadTestJDBC() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }

    @Test
    public void saveTestHibernate() throws Exception {
        try (DBService service = DBHelper.getDBServiceHibernateInstance()) {
            long id = service.save(
                new UserDataSet("Ivan", 2,
                    new AddressDataSet("Gorodeckaya"),
                        Arrays.asList(new PhoneDataSet("123"), new PhoneDataSet("456"))));
            System.out.println("New user with id=" + id + " was created");
            Assert.assertTrue(id != -1);
        }
    }

    @Test
    public void loadTestHibernate() throws Exception {
        try (DBService service = DBHelper.getDBServiceHibernateInstance()) {
            long id = service.save(
                new UserDataSet("Ivan", 2,
                    new AddressDataSet("Gorodeckaya"),
                        Arrays.asList(new PhoneDataSet("123"), new PhoneDataSet("456"))));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }
}