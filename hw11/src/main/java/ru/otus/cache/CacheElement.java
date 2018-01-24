package ru.otus.cache;

import java.lang.ref.SoftReference;

public class CacheElement<K, V> {
    private final K key;
    private final SoftReference<V> refValue;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(K key, V value) {
        this.key = key;
        this.refValue = new SoftReference<V>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return refValue.get();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}