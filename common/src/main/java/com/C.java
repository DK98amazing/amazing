package com;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: liguoyao
 */
public class C {
    private static Map<String, LongAdder> map = new HashMap<>();
    public static void put(String tableName) {
        if (!map.containsKey(tableName)) {
            LongAdder longAdder = new LongAdder();
            map.put(tableName, longAdder);
        }
        LongAdder longAdder = map.get(tableName);
        longAdder.increment();
        map.put(tableName, longAdder);
    }

    public static void main(String args[]) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = service.scheduleWithFixedDelay(new Runnable() {
            @Override public void run() {
                System.out.println(1);
            }
        }, 0, 10, TimeUnit.SECONDS);
        new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(scheduledFuture.isDone() + "---" + scheduledFuture.isCancelled());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    scheduledFuture.cancel(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static boolean getA() {
        System.out.println("A");
        return true;
    }

    private static boolean getB() {
        System.out.println("B");
        return false;
    }
}
