package ru.otus.dbserver.config;

import ru.otus.dbserver.entity.DataSet;

import java.util.HashSet;
import java.util.Set;

public class Configuration  {
    private final Set<Class> classes = new HashSet<>();

    public void addClass(Class<? extends DataSet> clazz) {
        this.classes.add(clazz);
    }

    public boolean isClassAdded(Class clazz) {
        return this.classes.contains(clazz);
    }
}
