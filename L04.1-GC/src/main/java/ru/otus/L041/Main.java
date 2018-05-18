package ru.otus.L041;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Запускать:
 * -XX:+UseSerialGC -Xmx256m -Xms256m
 * -XX:+UseParallelGC -Xmx256m -Xms256m
 * -XX:+UseConcMarkSweepGC -Xmx256m
 * -XX:+UseG1GC -Xmx256m -Xms256m
 * <p>
 * MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
 * GC1="-XX:+UseSerialGC -XX:+UseSerialGC"
 * GC2="-XX:+UseParallelGC -XX:+UseParallelOldGC"
 * GC3="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC"
 * GC4="-XX:+UseG1GC"
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int arraysCount = 10_000_000;
        int cleanWindowSize = 50;

        setupExecTime();
        InstallGCMonitoring gcMonitoring = new InstallGCMonitoring();
        gcMonitoring.init();

        Object[] arrays = new Object[arraysCount];
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = new Object[i * 1000];
            fillArray((Object[]) arrays[i]);
            if (i > cleanWindowSize) arrays[i - cleanWindowSize] = null;
        }
    }

    private static void fillArray(Object[] array) throws InterruptedException {
        for (int i = 0; i < array.length; i++) {
            array[i] = new String("");
            if (i % 100 == 0) Thread.sleep(1);
        }
    }

    private static void setupExecTime() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 300000);
    }
}
