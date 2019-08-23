import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.JavaVerticleFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.VerticleFactory;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;

public class MainClass {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        final Map<String, LongAdder> threadCounts = new ConcurrentHashMap<>();
        final CountDownLatch latch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            vertx.deployVerticle(new VerticleOne(threadCounts), deploymentOptions, event -> {
                if (event.succeeded()) {
//                System.out.println(event.result());
                } else {
                    System.out.println(event.cause().toString());
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Map.Entry entry : threadCounts.entrySet()) {
            System.out.println("线程： " + entry.getKey() + " = " + entry.getValue());
        }

        VerticleFactory verticleFactory = new JavaVerticleFactory();
        vertx.registerVerticleFactory(verticleFactory);

        System.err.println(vertx.getOrCreateContext().toString());
        System.err.println(vertx.getOrCreateContext().config().getString("name"));

        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("127.0.0.0", event -> {
            System.err.println("I have received a message: " + event.body());
        });

        Vertx vertxCluster = (Vertx) ZookeeperVertxCluster.getSettableFuture().get();
        EventBus eventBusCluster = vertxCluster.eventBus();
        eventBusCluster.consumer("10.0.7.173", event -> {
            System.err.println("I have received a message: " + event.body());
        });
    }
}
