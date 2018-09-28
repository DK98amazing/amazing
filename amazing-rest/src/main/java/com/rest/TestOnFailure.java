package com.rest;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

public class TestOnFailure {
    public static void main(String args[]) {
        Cache<Integer, Integer> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(2000, TimeUnit.MILLISECONDS).removalListener(
            (RemovalListener<Integer, Integer>) notification -> {
                if (notification.getCause() == RemovalCause.EXPIRED) {
                    System.out.println("out");
                }
            }).build(new CacheLoader<Integer, Integer>() {
            @Override public Integer load(Integer key) throws Exception {
                return key;
            }
        });

        loadingCache.put(1,1);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loadingCache.put(1,2);
        System.out.println(loadingCache.size());
//        loadingCache.invalidate(1);
//        try {
//            Thread.sleep(1200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        loadingCache.cleanUp();
        System.out.println(loadingCache.size());
    }
}
