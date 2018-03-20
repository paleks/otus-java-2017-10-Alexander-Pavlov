package ru.otus.webapp.services.cache;

import ru.otus.webapp.messagesystem.Addressee;

public interface CacheEngine<K, V> extends Addressee {

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

    public void init();

    public CacheInfo getCacheInfo();
}
