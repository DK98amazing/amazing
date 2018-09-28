import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class Test {
    public static volatile ListenableFuture<String> listenableFuture;
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        SettableFuture settableFuture = SettableFuture.create();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        service.scheduleWithFixedDelay(new Runnable() {
            @Override public void run() {
                settableFuture.set(Thread.currentThread().getName());
            }
        }, 1, 1, TimeUnit.SECONDS);
        service.scheduleWithFixedDelay(new Runnable() {
            @Override public void run() {
                try {
                    if (settableFuture.get() != null) {
                        settableFuture.set(null);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 2, TimeUnit.SECONDS);
        while (settableFuture.get() != null) {
            System.out.println(settableFuture.get());
            Thread.sleep(1000);
        }
        String before = null;String after = null;StringBuilder builder = new StringBuilder();
        String s = "period=2/ AND/ Fid='\\\\\\interface=587204096'";
        String[] strings = s.split("/");
        int count = strings.length;
        for (int i=0; i<count; i=i+2) {
            if (strings[i].contains("Fid")) {
                before = strings[i] + "/" + " AND/ ";
            } else if (strings[i].contains("period")) {
                after = strings[i];
            } else {
                builder.append(strings[i] + "/" + " AND/ ");
            }
        }
        System.out.println(before + builder.toString() + after);
        SettableFuture<Integer> future2 = SettableFuture.create();
        future2.set(3);
        ListenableFuture<Integer> future = Futures.transform(future2, new Function<Integer, Integer>() {
            @Nullable @Override public Integer apply(@Nullable Integer input) {
                return input+22;
            }
        });
        System.out.println(future.get());
        Future<Integer> future1 = Futures.lazyTransform(future2, new Function<Integer, Integer>() {
            @Nullable @Override public Integer apply(@Nullable Integer input) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return input + 3;
            }
        });
        future1.cancel(false);
        System.out.println(future1.isDone());
        System.out.println(future1.get());
        AtomicReference<HashMap> reference = new AtomicReference<>();
        reference.getAndSet(new HashMap());

        SettableFuture<String> future22 = SettableFuture.create();

        future22.cancel(false);
        System.out.println(future22.get());
        System.out.println(future22.isCancelled());


        AtomicReference<HashMap<String, String>> hashMapAtomicReference = new AtomicReference<>(new HashMap<>());
        hashMapAtomicReference.get().put("2", "2");
        hashMapAtomicReference.updateAndGet(UnaryOperator.identity());

        SettableFuture<String> settableFuture23 = SettableFuture.create();
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                settableFuture23.set("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName());
                while (true) {
                    if (!Thread.currentThread().isInterrupted()) {
                        System.out.println(listenableFuture.get());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                listenableFuture.cancel(true);
                System.out.println(listenableFuture.isCancelled());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        long l = Long.MAX_VALUE - 444;
        System.out.println((int) l);
    }
}
