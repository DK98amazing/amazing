import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.Calendar;

public class MainClass2 {
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

        Future future1 = Future.future(promise -> {
            System.out.println("future1 success");
            promise.complete();
        });
        Future future2 = Future.future(promise -> {
            System.out.println("future2 fail");
            promise.fail("fail");
        });
        CompositeFuture.any(future1, future2).setHandler(event -> {
            if (event.succeeded()) {
                System.out.println("all future success");
            } else {
                System.out.println("all future failed");
            }
        });

        System.out.println(Calendar.getInstance().getTime());

        DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(new JsonObject().put("name", "test").put("directory", "/bash"));

        vertx.deployVerticle("testVerticle", deploymentOptions, event -> {
            if (event.succeeded()) {
                System.out.println(event.result());
            } else {
                System.out.println(event.cause().toString());
            }
        });

        System.err.println(vertx.getOrCreateContext().toString());
        System.err.println(vertx.getOrCreateContext().config().getString("name"));

        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("127.0.0.0", event -> {
            System.err.println("I have received a message: " + event.body());
        });

        eventBus.send("10.0.7.173", "111111111111111111111111");
    }
}
