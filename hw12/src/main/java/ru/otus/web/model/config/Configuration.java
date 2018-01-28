package ru.otus.web.model.config;

import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.entity.DataSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration  {
    private final Set<Class> classes = new HashSet<>();
    private final Map<Class, CacheEngine<Long, ? extends DataSet>> cacheMap = new HashMap<>();

    public void addClass(Class<? extends DataSet> clazz, CacheEngine<Long, ? extends DataSet> cacheEngine) {
        this.classes.add(clazz);
        if (cacheEngine != null) {
            this.cacheMap.put(clazz, cacheEngine);
        }
    }

    public boolean isClassAdded(Class clazz) {
        return this.classes.contains(clazz);
    }

    public CacheEngine<Long, ? extends DataSet> getCacheEngine(Class clazz) {
        return this.cacheMap.get(clazz);
    }
}
