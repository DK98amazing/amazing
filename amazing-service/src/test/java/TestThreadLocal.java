import java.util.concurrent.Executors;

public class TestThreadLocal {
    public static void main(String args[]) {
        final ThreadLocal<JarsLink> threadLocal = new ThreadLocal<JarsLink>();
        for (int i=0; i<100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (null == threadLocal.get()) {
                        threadLocal.set(new JarsLink());
                        System.out.println("-------------------------------");
                    }
                    System.out.println(threadLocal.get() + " ");
                }
            }).start();
        }
        threadLocal.remove();

    }
}
