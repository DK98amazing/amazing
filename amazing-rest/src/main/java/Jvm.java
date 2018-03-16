import org.junit.Test;

public class Jvm {
    static {
        i = 0;
//        System.out.println(i);
    }
    public static int i = 0;
    @Test
    public void runA() {
        System.out.println(Jvm.i);
    }
}
