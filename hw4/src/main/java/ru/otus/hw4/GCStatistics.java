package ru.otus.hw4;

public class GCStatistics {
    private int youngGCQuantity;
    private int oldGCQuantity;
    private long totalYoungGCDuration;
    private long totalOldGCDuration;

    public int getYoungGCQuantity() {
        return youngGCQuantity;
    }

    public void setYoungGCQuantity(int youngGCQuantity) {
        this.youngGCQuantity = youngGCQuantity;
    }

    public int getOldGCQuantity() {
        return oldGCQuantity;
    }

    public void setOldGCQuantity(int oldGCQuantity) {
        this.oldGCQuantity = oldGCQuantity;
    }

    public long getTotalYoungGCDuration() {
        return totalYoungGCDuration;
    }

    public void setTotalYoungGCDuration(long totalYoungGCDuration) {
        this.totalYoungGCDuration = totalYoungGCDuration;
    }

    public long getTotalOldGCDuration() {
        return totalOldGCDuration;
    }

    public void setTotalOldGCDuration(long totalOldGCDuration) {
        this.totalOldGCDuration = totalOldGCDuration;
    }

    public void clean() {
        this.youngGCQuantity = 0;
        this.oldGCQuantity = 0;
        this.totalYoungGCDuration = 0;
        this.totalOldGCDuration = 0;
    }

    @Override
    public String toString() {
        return "GCStatistics{" +
                "youngGCQuantity=" + youngGCQuantity +
                ", oldGCQuantity=" + oldGCQuantity +
                ", totalYoungGCDuration=" + totalYoungGCDuration +
                ", totalOldGCDuration=" + totalOldGCDuration +
                '}';
    }

    public void print() {
        System.out.println(this.toString());
    }
}
