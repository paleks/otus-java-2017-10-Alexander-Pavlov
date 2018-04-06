package ru.otus.messageserver.app;

public class CacheInfo {
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final int hit;
    private final int miss;

    public CacheInfo(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal, int hit, int miss) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
        this.hit = hit;
        this.miss = miss;
    }

    public int getMaxElements() {
        return maxElements;
    }

    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    public long getIdleTimeMs() {
        return idleTimeMs;
    }

    public boolean isEternal() {
        return isEternal;
    }

    public int getHit() {
        return hit;
    }

    public int getMiss() {
        return miss;
    }

    @Override
    public String toString() {
        return "CacheInfo{" +
                "maxElements=" + maxElements +
                ", lifeTimeMs=" + lifeTimeMs +
                ", idleTimeMs=" + idleTimeMs +
                ", isEternal=" + isEternal +
                ", hit=" + hit +
                ", miss=" + miss +
                '}';
    }
}
