package ru.otus.web.model.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine <K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

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
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        elements.put(key, element);

        if (!isEternal) {
            // lifeTimeMs == 0
            TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
            timer.schedule(lifeTimerTask, lifeTimeMs);
            // idleTimeMs == 0
            TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
            timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
        }
    }

    @Override
    public CacheElement<K, V> get(K key) {
        CacheElement<K, V> element = elements.get(key);
        if (element.getValue() != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    @Override
    public boolean contains(K key) {
        return this.elements.containsKey(key);
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

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public String toString() {
        return "CacheEngineImpl{" +
                "maxElements=" + maxElements +
                ", lifeTimeMs=" + lifeTimeMs +
                ", idleTimeMs=" + idleTimeMs +
                ", isEternal=" + isEternal +
                ", hit=" + hit +
                ", miss=" + miss +
                '}';
    }
}