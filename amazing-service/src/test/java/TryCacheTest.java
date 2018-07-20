import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TryCacheTest {
    public static void main(String args[]) throws InterruptedException {
        Tests tests = new Tests("1", 1);
        System.out.println((tests.param));
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        List<Thread> list = Lists.newArrayList();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("1","1");
            }
        });t1.start();
        list.add(t1);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("1","2");
            }
        });t2.start();
        list.add(t2);
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("1","3");
            }
        });t3.start();
        list.add(t3);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("1","5");
            }
        });
        t.start();
        list.add(t);
        try {
            for (Thread thread : list) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.sleep(500);
        System.out.println(map.get("1"));

        ExecutorService service = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "sss");
            }
        });
        System.out.println(new Date());
//        ((ScheduledExecutorService) service).scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(new Date());
//                System.out.println(Thread.currentThread().getName() + " is Running");
//            }
//        }, 2, 1, TimeUnit.SECONDS);
        ((ScheduledExecutorService) service).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date());
                System.out.println(Thread.currentThread().getName() + " is Running");
            }
        }, 2, 3, TimeUnit.SECONDS);
    }
}
