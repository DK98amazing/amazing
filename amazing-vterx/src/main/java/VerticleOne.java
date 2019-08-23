import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class VerticleOne extends AbstractVerticle {
    private HttpServer httpServer;
    private Map<String, LongAdder> threadCount;

    public VerticleOne(Map<String, LongAdder> threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        threadCount.computeIfAbsent(Thread.currentThread().getName(), s -> {
            return new LongAdder();
        }).increment();
        startPromise.complete();
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);
        httpServer = vertx.createHttpServer().requestHandler(request -> {
            request.response().putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x");
        });

        threadCount.computeIfAbsent(Thread.currentThread().getName(), s -> {
            return new LongAdder();
        }).increment();

        httpServer.listen(8080, event -> {
            if (event.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(event.cause());
            }
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
        stopFuture.complete();
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        stopPromise.complete();
    }
}
