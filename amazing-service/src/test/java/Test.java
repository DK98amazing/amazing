import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        String ss = "123";
//        SettableFuture settableFuture = SettableFuture.create();
//        settableFuture.set(ss);
        ListenableFuture<Integer> listenableFuture2 = Futures.immediateFuture(122334455);
        ListenableFuture<String> listenableFuture = ListenableFutureTask.create(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return ss;
            }
        });
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println(12333);
            }
        }, MoreExecutors.directExecutor());
        ((ListenableFutureTask<String>) listenableFuture).run();
        System.out.println(listenableFuture.isDone());
        listenableFuture.get();
        System.out.println(listenableFuture2.get());
//        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
//            @Override
//            public void onSuccess(@Nullable String s) {
//                try {
//                    System.out.println(listenableFuture.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                System.out.println("failure");
//            }
//        });
    }
}
