package ru.otus.web.model.config;

import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.entity.DataSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration  {
    private final Set<Class> classes = new HashSet<>();
    private CacheEngine<String, ? extends DataSet> cacheEngine;

    public void addClass(Class<? extends DataSet> clazz) {
        this.classes.add(clazz);
    }

    public CacheEngine<String, ? extends DataSet> getCacheEngine() {
        return cacheEngine;
    }

    public void addCacheEngine(CacheEngine<String, ? extends DataSet> cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    public boolean isClassAdded(Class clazz) {
        return this.classes.contains(clazz);
    }
}
