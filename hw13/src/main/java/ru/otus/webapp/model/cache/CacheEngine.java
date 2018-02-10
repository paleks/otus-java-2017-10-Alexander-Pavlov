package ru.otus.webapp.model.cache;

public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    CacheElement<K, V> get(K key);

    boolean contains(K key);

    int getMaxElements();

    long getLifeTime();

    long getIdleTime();

    boolean isEternal();

    int getHitCount();

    int getMissCount();

    void dispose();
}
