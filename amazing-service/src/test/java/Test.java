import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;
import scala.Int;

import javax.annotation.Nullable;
import javax.xml.crypto.Data;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.*;

public class Test {
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

    }
}
