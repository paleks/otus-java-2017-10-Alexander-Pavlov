package ru.otus.service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.cache.CacheEngineImpl;
import ru.otus.entity.UserDataSet;
import ru.otus.util.DBHelper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

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
    public void saveLoadTest() throws Exception {
        try (DBService service = DBHelper.getDBServiceInstance()) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(user);
            Assert.assertTrue(user.getId() == id);
        }
    }

    @Test
    public void saveCacheTest() throws Exception {
        try (DBService service =
                     DBHelper.getDBServiceInstance(new CacheEngineImpl<>(5, 0, 0, true))) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            System.out.println(service.getCacheEngine().toString());
            Assert.assertTrue(service.getCacheEngine().get(id) != null);
        }
    }

    @Test
    public void saveLoadCacheHitTest() throws Exception {
        try (DBService service =
                     DBHelper.getDBServiceInstance(new CacheEngineImpl<>(5, 0, 0, true))) {
            long id = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id + " was created");
            UserDataSet user = service.load(id, UserDataSet.class);
            System.out.println(service.getCacheEngine().toString());
            Assert.assertTrue(service.getCacheEngine().getHitCount() == 1);
        }
    }

    @Test
    public void saveLoadCacheHitMissTest() throws Exception {
        try (DBService service =
                     DBHelper.getDBServiceInstance(new CacheEngineImpl<>(5, 0, 0, true))) {
            long id1 = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id1 + " was created");
            long id2 = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id2 + " was created");
            long id3 = service.save(new UserDataSet("Ivan", 2));
            System.out.println("New user with id=" + id3 + " was created");

            int size = 2000;
            List<SoftReference<BigObject>> references = new ArrayList<>(size);

            for (int k = 0; k < size; k++) {
                references.add(new SoftReference<>(new BigObject()));
            }

            //System.gc();

            int sum = 0;
            for (int k = 0; k < size; k++) {
                if (references.get(k).get() != null) sum++;
            }

            System.out.println("Soft references: " + sum);

            UserDataSet user = service.load(id1, UserDataSet.class);
            System.out.println(user.toString());
            user = service.load(id2, UserDataSet.class);
            System.out.println(user.toString());
            user = service.load(id3, UserDataSet.class);
            System.out.println(user.toString());
            System.out.println(service.getCacheEngine().toString());
            Assert.assertTrue(service.getCacheEngine().getMissCount() == 3);
        }
    }

    @Test
    public void maxElementTest() {

    }

    @Test
    public void lifeTimeTest() {

    }

    @Test
    public void idleTimeTest() {

    }

    static class BigObject {
        final byte[] array = new byte[1024 * 1024];

        public byte[] getArray() {
            return array;
        }
    }
}