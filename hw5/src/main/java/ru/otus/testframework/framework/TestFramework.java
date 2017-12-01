package ru.otus.testframework.framework;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;
import ru.otus.testframework.util.ReflectionHelper;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

public class TestFramework {
    public static void test(Class clazz) {
        List<Method> testMethods = ReflectionHelper.getAnnotatedMethods(clazz, Test.class);
        List<Method> afterMethods = ReflectionHelper.getAnnotatedMethods(clazz, After.class);
        List<Method> beforeMethods = ReflectionHelper.getAnnotatedMethods(clazz, Before.class);
        if (afterMethods.size() > 1 || beforeMethods.size() > 1) {
            throw new RuntimeException("Test contract is violated");
        }

        testMethods.forEach(method -> {
            Object clazzInstance = ReflectionHelper.instantiate(clazz);
            ReflectionHelper.callMethod(clazzInstance, beforeMethods.get(0).getName());
            ReflectionHelper.callMethod(clazzInstance, method.getName());
            ReflectionHelper.callMethod(clazzInstance, afterMethods.get(0).getName());
        });
    }

    public static void test(String pack) {
        URL resource = ClassLoader.getSystemClassLoader().
                getResource(pack.replace('.', '/'));
        if (resource == null) {
            throw new RuntimeException("Package is not found");
        }
        File dir = new File(resource.getFile());
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                try {
                    TestFramework.test(Class.forName(pack + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
