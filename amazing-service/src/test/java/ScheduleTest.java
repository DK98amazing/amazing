import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ScheduleTest.
 *
 * @author liguoyao
 */
public class ScheduleTest {
    public static void main(String args[]) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = service.schedule(() -> {
            for (; ; ) {
                System.out.println("---");
            }
        }, 1, TimeUnit.MILLISECONDS);
        new Thread(() -> {
            for (int i=0; i<50; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(111);
            }
//            scheduledFuture.cancel(true);
        }).start();
    }
}
