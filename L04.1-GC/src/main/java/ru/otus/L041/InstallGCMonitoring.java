package ru.otus.L041;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

public class InstallGCMonitoring {

    private String name;
    private long duration;

    private ArrayList<String> gcNames = new ArrayList<>();
    private Map<String, Integer> gcCollections = new HashMap<>();
    private Map<String, Long> gcDurations = new HashMap<>();

    public void init() {
        initGcListener();
        System.out.println("Note: GC info shows every one minute");
        String line = "------------------------------+----------+---------------";
        System.out.println(line);
        System.out.println(
                String.format("%-30s|%-10s|%-15s",
                        "Action",
                        "Count",
                        "Total Time, s"));
        System.out.println(line);
        initLogger();
    }

    private void initGcListener() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            gcNames.add(gcbean.getName());
            gcCollections.put(gcbean.getName(), 0);
            gcDurations.put(gcbean.getName(), 0L);
            emitter.addNotificationListener(new NotificationListenerImpl(), null, null);
        }
    }

    private class NotificationListenerImpl implements NotificationListener {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.
                        from((CompositeData) notification.getUserData());
                name = info.getGcName();
                duration = info.getGcInfo().getDuration();
                gcCollections.put(name, gcCollections.get(name) + 1);
                gcDurations.put(name, gcDurations.get(name) + duration);
            }
        }
    }

    private void initLogger() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(
                        String.format("%-30s|%-10d|%.3f",
                                gcNames.get(0),
                                gcCollections.get(gcNames.get(0)),
                                ((double) gcDurations.get(gcNames.get(0)) / 1000)));

                System.out.println(
                        String.format("%-30s|%-10d|%.3f",
                                gcNames.get(1),
                                gcCollections.get(gcNames.get(1)),
                                ((double) gcDurations.get(gcNames.get(1)) / 1000)));

                gcCollections.put(gcNames.get(0), 0);
                gcDurations.put(gcNames.get(0), 0L);
                gcCollections.put(gcNames.get(1), 0);
                gcDurations.put(gcNames.get(1), 0L);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 60000);
    }
}
