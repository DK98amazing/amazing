import org.junit.Test;

import java.net.URL;

public class JarsLink {
    @Test
    public void runA() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("jarslink-module-demo-1.0.0.jar");
    }
}
