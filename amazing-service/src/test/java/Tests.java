import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Tests {
    private String string;
    private int anInt;
    public String param = "121212";

    public Tests(String string, int anInt) {
        this.string = string;
        this.anInt = anInt;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(3 * Math.pow(2, 12));
//        System.out.print(3 << 12);
//        Thread thread = new Thread(new Runnable() {
//            int i = 0;
//            @Override
//            public void run() {
//                try {
//                    for (;;) {
//                        TimeUnit.SECONDS.sleep(1);
//                        System.out.println(i ++);
//                        if (i > 10) {
//                            LockSupport.park();
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.setName("park thread");
//        thread.start();
//        Thread thread1 = new Thread(() -> {
//            while (true) {
//                if (null == LockSupport.getBlocker(thread)) {
//                    LockSupport.unpark(thread);
//                }
//            }
//        });
//        thread1.setName("unpark thread");
//        thread1.start();


        System.out.println(new BigDecimal(30).multiply(new BigDecimal(1.1).pow(1)).intValue());
        System.out.println(0.2 * 0.1);
        System.out.println(2.4f);
    }
}
