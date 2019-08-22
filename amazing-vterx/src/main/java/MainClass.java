import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;

public class MainClass {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
        WorkerExecutor workerExecutor = vertx.createSharedWorkerExecutor("my_work_pool");
        workerExecutor.executeBlocking(promise -> {
            try {
                Thread.sleep(5000);
                promise.complete("qqqq");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, false, re -> {
            System.out.println("Result: " + re.result());
        });
        vertx.setPeriodic(100000, id -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Periodic fired!");
        });
        vertx.setTimer(1000, id -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Timer fired!");
        });
    }
}
