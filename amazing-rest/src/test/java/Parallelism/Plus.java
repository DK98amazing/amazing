package Parallelism;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Plus implements Runnable {
    public static BlockingQueue<Msg> queue = new LinkedBlockingQueue<>();
    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = queue.take();
                msg.j = msg.j + msg.i;
                Multiply.queue.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
