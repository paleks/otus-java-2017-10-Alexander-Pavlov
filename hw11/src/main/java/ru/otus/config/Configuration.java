package ru.otus.config;

import ru.otus.cache.CacheEngine;
import ru.otus.entity.DataSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration  {
    private final Set<Class> classes = new HashSet<>();
    private final Map<Class, CacheEngine> cacheMap = new HashMap<>();

    public void addClass(Class<? extends DataSet> clazz, CacheEngine<Long, ? extends DataSet> cacheEngine) {
        this.classes.add(clazz);
        if (cacheEngine != null) {
            this.cacheMap.put(clazz, cacheEngine);
        }
    }

    public Set<Class> getClasses() {
        return classes;
    }

    public Map<Class, CacheEngine> getCacheMap() {
        return cacheMap;
    }

    public CacheEngine<Long, DataSet> getCacheEngine(Class clazz) {
        return this.cacheMap.get(clazz);
    }
}
