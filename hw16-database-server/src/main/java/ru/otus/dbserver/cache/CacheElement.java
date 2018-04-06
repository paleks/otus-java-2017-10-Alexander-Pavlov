package ru.otus.dbserver.cache;

import java.lang.ref.SoftReference;

public class CacheElement<K, V> {
    private final K key;
    private final SoftReference<V> refValue;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(K key, V value) {
        this.key = key;
        this.refValue = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    K getKey() {
        return key;
    }

    public V getValue() {
        return refValue.get();
    }

    long getCreationTime() {
        return creationTime;
    }

    long getLastAccessTime() {
        return lastAccessTime;
    }

    void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}