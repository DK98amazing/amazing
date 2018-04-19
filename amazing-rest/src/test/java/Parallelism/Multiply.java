package Parallelism;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Multiply implements Runnable {
    public static BlockingQueue<Msg> queue = new LinkedBlockingQueue<>();
    @Override
    public void run() {
        int flag = 0;
        try {
            while (true) {
                Msg msg = queue.take();
                msg.i = msg.j * msg.i;
                System.out.println(msg.orgStr + " " + msg.i + " 第 " + ++flag + "次" );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
