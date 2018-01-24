package ru.otus.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

public class CacheEngineImpl<K, V> implements CacheEngine <K, V>{

    private final Timer timer = new Timer();
    private final Map<K, CacheElement<K, V>> elements = new LinkedHashMap();

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        elements.put(element.getKey(), element);
    }

    @Override
    public CacheElement<K, V> get(K key) {
        return elements.get(key);
    }

    @Override
    public int getHitCount() {
        return this.hit;
    }

    @Override
    public int getMissCount() {
        return this.miss;
    }

    @Override
    public void dispose() {
        this.timer.cancel();
    }
}
