package ru.otus.service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.cache.CacheEngine;
import ru.otus.cache.CacheEngineImpl;
import ru.otus.config.Configuration;
import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImplTest {

    @Test(expected = UnsupportedOperationException.class)
    public void exceptionTest() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance(new Configuration())) {
            service.save(new UserDataSet("Ivan", 2));
        }
    }

    @Test
    public void saveTest() throws Exception {
        Configuration config = new Configuration();
        config.addClass(UserDataSet.class, null);

        try (DBService service = DBHelper.getDBServiceInstance(config)) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            Assert.assertTrue(id != -1);
        }
    }

    @Test
    public void saveLoadTest() throws Exception {
        Configuration config = new Configuration();
        config.addClass(UserDataSet.class, null);

        try (DBService service = DBHelper.getDBServiceInstance(config)) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }

    @Test
    public void saveCacheTest() throws Exception {
        Configuration config = new Configuration();
        CacheEngine<Long, UserDataSet> cacheEngine = new CacheEngineImpl<>(5, 0, 0, true);
        config.addClass(UserDataSet.class, cacheEngine);

        try (DBService service = DBHelper.getDBServiceInstance(config)) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            System.out.println(cacheEngine.toString());
            Assert.assertTrue(cacheEngine.get(id) != null);
        }
    }

    @Test
    public void saveLoadCacheHitTest() throws Exception {
        Configuration config = new Configuration();
        CacheEngine<Long, UserDataSet> cacheEngine = new CacheEngineImpl<>(5, 0, 0, true);
        config.addClass(UserDataSet.class, cacheEngine);

        try (DBService service = DBHelper.getDBServiceInstance(config)) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            service.load(id, UserDataSet.class);
            System.out.println(cacheEngine.toString());
            Assert.assertTrue(cacheEngine.getHitCount() == 1);
        }
    }

    @Test
    public void saveLoadCacheHitMissTest() throws Exception {
        Configuration config = new Configuration();
        CacheEngine<Long, UserDataSet> cacheEngine =
                new CacheEngineImpl<>(5, 0, 0, true);
        config.addClass(UserDataSet.class, cacheEngine);

        try (DBService service = DBHelper.getDBServiceInstance(config)) {
            long id1 = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id1 + " was created");
            long id2 = service.save(new UserDataSet("Alex", 10));
            System.out.println("New user with id=" + id2 + " was created");
            long id3 = service.save(new UserDataSet("Dmitry", 22));
            System.out.println("New user with id=" + id3 + " was created");

            dummyLoad();

            UserDataSet user = service.load(id1, UserDataSet.class);
            System.out.println(user.toString());
            user = service.load(id2, UserDataSet.class);
            System.out.println(user.toString());
            user = service.load(id3, UserDataSet.class);
            System.out.println(user.toString());
            System.out.println(cacheEngine.toString());
            // Is cache clean ?
            Assert.assertTrue(cacheEngine.getMissCount() == 3 && cacheEngine.getHitCount() == 0);
        }
    }

    private void dummyLoad() {
        int size = 2000;
        List<SoftReference<BigObject>> references = new ArrayList<>(size);

        for (int k = 0; k < size; k++) {
            references.add(new SoftReference<>(new BigObject()));
        }

        int sum = 0;
        for (int k = 0; k < size; k++) {
            if (references.get(k).get() != null) sum++;
        }

        System.out.println("Soft references: " + sum);
    }


    static class BigObject {
        final byte[] array = new byte[1024 * 1024];
    }
}