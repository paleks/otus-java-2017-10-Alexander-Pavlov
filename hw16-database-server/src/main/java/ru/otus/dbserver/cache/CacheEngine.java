package ru.otus.dbserver.cache;

public interface CacheEngine<K, V> {

    public void put(CacheElement<K, V> element);

    public CacheElement<K, V> get(K key);

    public boolean contains(K key);

    public int getMaxElements();

    public long getLifeTime();

    public long getIdleTime();

    public boolean isEternal();

    public int getHitCount();

    public int getMissCount();

    public void dispose();

    public CacheInfo getCacheInfo();
}
