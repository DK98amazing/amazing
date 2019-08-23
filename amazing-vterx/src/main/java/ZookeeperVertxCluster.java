import com.google.common.util.concurrent.SettableFuture;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.atomic.AtomicReference;

public class ZookeeperVertxCluster {
    public static SettableFuture<Vertx> settableFuture = SettableFuture.create();

    public static SettableFuture<Vertx> getSettableFuture() {
        return settableFuture;
    }

    static {
        AtomicReference<Vertx> vertxCluster = new AtomicReference<>();
        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "10.0.7.173");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));
        ClusterManager clusterManager = new ZookeeperClusterManager(zkConfig);

        VertxOptions options = new VertxOptions().setClusterManager(clusterManager)
                .setEventBusOptions(new EventBusOptions()
//                        .setSsl(true)
//                        .setKeyStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("wibble"))
//                        .setTrustStoreOptions(new JksOptions().setPath("keystore.jks").setPassword("wibble"))
//                        .setClientAuth(ClientAuth.REQUIRED)
                                .setClustered(true)
                );

        try {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    vertxCluster.set(res.result());
                    settableFuture.set(vertxCluster.get());
                    EventBus eventBusCluster = vertxCluster.get().eventBus();
                    System.out.println("We now have a clustered event bus: " + eventBusCluster);
                } else {
                    settableFuture.setException(new RuntimeException("vertxCluster failed"));
                    System.out.println("Failed: " + res.cause());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        clusterManager.join(event -> {
            System.out.println("####################### join");
        });
        clusterManager.leave(event -> {
            System.out.println("####################### leave");
        });
    }
}
