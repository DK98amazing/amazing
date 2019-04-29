package springboot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class TestAll {
    private static ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        ThreadGroup group = (System.getSecurityManager() != null) ? System.getSecurityManager().getThreadGroup() :
            Thread.currentThread().getThreadGroup();
        @Override public Thread newThread(Runnable r) {
            return new Thread(group, r,
                "Reconnect task execute thread",0);
        }
    });

    public static void main(String[] args) throws InterruptedException, IOException {
        ScheduledFuture<?> scheduledFuture = scheduledExecutor.schedule(() -> System.out.println(111), 5, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(2);
        if (!scheduledFuture.isDone()) {
            System.out.println("cancel");
            scheduledFuture.cancel(true);
        }
        Map<String,String> map = System.getenv();
        Properties properties = new Properties();
        properties.setProperty("456", "456");
        System.setProperties(properties);
        System.out.println(System.getProperty("456"));
    }
}
