package ru.otus.hw4;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

public class GCNotificationListener implements NotificationListener {
    private GCStatistics statistics = new GCStatistics();

    public GCStatistics getStatistics() {
        return this.statistics;
    }

    public static GCNotificationListener listener = new GCNotificationListener();

    private GCNotificationListener() {

    }

    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            long duration = info.getGcInfo().getDuration();
            String gctype = info.getGcAction();
            if ("end of minor GC".equals(gctype)) {
                gctype = "Young Gen GC";
                this.statistics.setYoungGCQuantity(this.statistics.getYoungGCQuantity() + 1);
                this.statistics.setTotalYoungGCDuration(this.statistics.getTotalYoungGCDuration() + info.getGcInfo().getDuration());
            } else if ("end of major GC".equals(gctype)) {
                gctype = "Old Gen GC";
                this.statistics.setOldGCQuantity(this.statistics.getOldGCQuantity() + 1);
                this.statistics.setTotalOldGCDuration(this.statistics.getTotalOldGCDuration() + info.getGcInfo().getDuration());
            }
            System.out.println();
            System.out.println(gctype + ": - " +
                    info.getGcInfo().getId()+ " " +
                    info.getGcName() + " (from " +
                    info.getGcCause()+") " + duration + " milliseconds; start-end times " +
                    info.getGcInfo().getStartTime()+ "-" +
                    info.getGcInfo().getEndTime());
        }
    }
}
