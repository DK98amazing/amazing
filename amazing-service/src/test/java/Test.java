import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;
import jnr.ffi.annotations.In;
import scala.Int;
import scala.ScalaReflectionException;

import javax.annotation.Nullable;
import javax.xml.crypto.Data;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

public class Test {
    public static volatile ListenableFuture<String> listenableFuture;
    public static void main(String args[]) throws ExecutionException, InterruptedException {
//        SettableFuture settableFuture = SettableFuture.create();
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
//        service.scheduleWithFixedDelay(new Runnable() {
//            @Override public void run() {
//                settableFuture.set(Thread.currentThread().getName());
//            }
//        }, 1, 1, TimeUnit.SECONDS);
//        service.scheduleWithFixedDelay(new Runnable() {
//            @Override public void run() {
//                try {
//                    if (settableFuture.get() != null) {
//                        settableFuture.set(null);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1, 2, TimeUnit.SECONDS);
//        while (settableFuture.get() != null) {
//            System.out.println(settableFuture.get());
//            Thread.sleep(1000);
//        }
//        String before = null;String after = null;StringBuilder builder = new StringBuilder();
//        String s = "period=2/ AND/ Fid='\\\\\\interface=587204096'";
//        String[] strings = s.split("/");
//        int count = strings.length;
//        for (int i=0; i<count; i=i+2) {
//            if (strings[i].contains("Fid")) {
//                before = strings[i] + "/" + " AND/ ";
//            } else if (strings[i].contains("period")) {
//                after = strings[i];
//            } else {
//                builder.append(strings[i] + "/" + " AND/ ");
//            }
//        }
//        System.out.println(before + builder.toString() + after);
//        SettableFuture<Integer> settableFuture = SettableFuture.create();
//        settableFuture.set(3);
//        ListenableFuture<Integer> future = Futures.transform(settableFuture, new Function<Integer, Integer>() {
//            @Nullable @Override public Integer apply(@Nullable Integer input) {
//                return input+22;
//            }
//        });
//        System.out.println(future.get());
//        Future<Integer> future1 = Futures.lazyTransform(settableFuture, new Function<Integer, Integer>() {
//            @Nullable @Override public Integer apply(@Nullable Integer input) {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return input + 3;
//            }
//        });
//        future1.cancel(false);
//        System.out.println(future1.isDone());
//        System.out.println(future1.get());
//        AtomicReference<HashMap> reference = new AtomicReference<>();
//        reference.getAndSet(new HashMap());

        SettableFuture<String> future = SettableFuture.create();

        future.cancel(false);
        System.out.println(future.get());
        System.out.println(future.isCancelled());


//        AtomicReference<HashMap<String, String>> reference = new AtomicReference<>(new HashMap<>());
//        reference.get().put("2", "2");
//        reference.updateAndGet(UnaryOperator.identity());
//
//        SettableFuture<String> settableFuture = SettableFuture.create();
//        new Thread(() -> {
//            try {
//                Thread.sleep(10000);
//                settableFuture.set("2");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(500);
//                System.out.println(Thread.currentThread().getName());
//                while (true) {
//                    if (!Thread.currentThread().isInterrupted()) {
//                        System.out.println(listenableFuture.get());
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName());
//                Thread.sleep(1000);
//                listenableFuture.cancel(true);
//                System.out.println(listenableFuture.isCancelled());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }
}
