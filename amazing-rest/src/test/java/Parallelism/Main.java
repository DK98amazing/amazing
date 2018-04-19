package Parallelism;

public class Main {
    public static void main(String args[]) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();

        for (int i=0; i<1000; i++) {
            for (int j=0; j<1000; j++) {
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                Plus.queue.add(msg);
            }
        }

    }
}
