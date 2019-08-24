import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;

public class VerticleHttp extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        HttpServerOptions httpServerOptions = new HttpServerOptions().setHost("127.0.0.1")
                .setPort(8082).setTcpNoDelay(true);
        HttpServer server = vertx.createHttpServer(httpServerOptions);

        server.requestHandler(request -> {

            // This handler gets called for each request that arrives on the server
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World!");
        });

        server.listen(8081);
        startPromise.complete();
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new VerticleHttp());
    }
}
