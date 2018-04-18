import javafx.scene.effect.SepiaTone;
import jnr.ffi.annotations.In;
import org.junit.Test;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class JarsLink {
    @Test
    public void runA() {
//        URL url = Thread.currentThread().getContextClassLoader().getResource("jarslink-module-demo-1.0.0.jar");
        Set<Integer> set = new HashSet<Integer>();
        String s = new String("45d6a45d4ad45a6s4d545e45e4");
        for (int i=0; i<1000; i++) {
            System.out.println(set.add(s.hashCode()));
        }
        System.out.println(set.size());
        switch (1) {
            case 1: System.out.println("111111");
        }
    }
}
