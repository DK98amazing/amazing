import com.hazelcast.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.ConfigUtil;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class HazelcastVertxCluster {
    public static void main(String args[]) {

        Config hazelcastConfig = ConfigUtil.loadConfig();

        hazelcastConfig.getGroupConfig()
                .setName("my-cluster-name")
                .setPassword("passwd");

        ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);

        VertxOptions options = new VertxOptions().setClusterManager(mgr);

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
            } else {
                // failed!
            }
        });
        mgr.join(event -> {
            System.out.println("******** join");
        });
        mgr.leave(event -> {
            System.out.println("******** leave");
        });
    }
}
