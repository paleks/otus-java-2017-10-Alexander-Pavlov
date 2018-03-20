package ru.otus.webapp.services.cache;

public class CacheInfo {

    private int maxElements;
    private long lifeTimeMs;
    private long idleTimeMs;
    private boolean isEternal;

    private int hit = 0;
    private int miss = 0;

    public int getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }

    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    public void setLifeTimeMs(long lifeTimeMs) {
        this.lifeTimeMs = lifeTimeMs;
    }

    public long getIdleTimeMs() {
        return idleTimeMs;
    }

    public void setIdleTimeMs(long idleTimeMs) {
        this.idleTimeMs = idleTimeMs;
    }

    public boolean isEternal() {
        return isEternal;
    }

    public void setEternal(boolean eternal) {
        isEternal = eternal;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }
}
