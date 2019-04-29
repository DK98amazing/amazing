package com.rest;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SSS.
 *
 * @author liguoyao
 */
public class SSS {
    private static LoadingCache<Class, ExecutorService> serviceCache =
        CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.SECONDS)
            .removalListener((RemovalListener<Class, ExecutorService>) notification -> {
                System.out.println(notification.getKey().getSimpleName());
                notification.getValue().shutdown();
            }).build(new CacheLoader<Class, ExecutorService>() {
            @Override public ExecutorService load(Class key) {
                System.out.println("load");
                return Executors.newSingleThreadExecutor(r -> new Thread(r, key.getSimpleName() + "Handler"));
            }
        });

    /**
     * Get SingleThreadExecutor.
     *
     * @param cls the current class implements DataTreeChangeListener.
     * @return SingleThreadExecutor.
     */
    public static ExecutorService getInstance(Class cls) {
        try {
            return serviceCache.get(cls);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws InterruptedException {
        getInstance(SSS.class).submit(() -> {
            System.out.println("SSS123");
        });
        TimeUnit.SECONDS.sleep(3);
        getInstance(A.class).submit(() -> {
            System.out.println("A123");
        });
        TimeUnit.SECONDS.sleep(4);
        serviceCache.cleanUp();

    }
}
