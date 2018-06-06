import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThreadLocal {
    private static Map<String, String> map = new ConcurrentHashMap<String, String>();
    private static Lock lock = new ReentrantLock();
    static {
        map.put("1", "1");
    }
    static class Executo implements Runnable {
        @Override
        public void run() {
            try {
//                map.put("1", "1");
                try {
                    lock.lock();
                    Thread.sleep(5 * 1000);
                    System.out.println(Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
//                synchronized (map.get("1")) {
//                    Thread.sleep(5 * 1000);
//                    System.out.println(Thread.currentThread().getName());
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String args[]) {
        Executo executo = new Executo();
        new Thread(executo).start();
        new Thread(executo).start();
    }

    @Test
    public void runA() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
    }
}
