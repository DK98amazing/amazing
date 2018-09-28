import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;

import javax.annotation.Nullable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestReference {
    public static void main(String args[]) throws InterruptedException {
        Object object = new Object();
        System.gc();
        System.out.println(object);
        SoftReference<Object> softReference = new SoftReference<>(new Object());
        System.gc();
        System.out.println(softReference);
        WeakReference<Object> weakReference = new WeakReference<>(new Object());
        System.gc();
        System.out.println(weakReference);

        SettableFuture<Integer> settableFuture = SettableFuture.create();
        settableFuture.set(new Integer(1));
        System.out.println(Thread.currentThread().getName());
        Futures.addCallback(settableFuture, new FutureCallback<Integer>() {
            @Override public void onSuccess(@Nullable Integer result) {
                System.out.println(Thread.currentThread().getName());
            }

            @Override public void onFailure(Throwable t) {

            }
        });
    }
}
