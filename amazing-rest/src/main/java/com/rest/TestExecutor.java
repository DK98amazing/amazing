package com.rest;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;

public class TestExecutor implements Runnable {
    public static void main(String args[]) {
////        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS)
//        try {
//            SettableFuture<Integer> settableFuture = SettableFuture.create();
//            settableFuture.setException(new IllegalStateException(""));
//            Futures.addCallback(settableFuture, new FutureCallback<Integer>() {
//                @Override public void onSuccess(@Nullable Integer result) {
//
//                }
//
//                @Override public void onFailure(Throwable t) {
//                    throw new IllegalStateException("123");
//                }
//            });
//        } catch (Exception e) {
//            if (e.getMessage().equals("123")) {
//                System.out.println(123);
//            }
//        }

        Thread thread = new Thread(new TestExecutor());
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e.getMessage());
            }
        });
        thread.start();
    }

    @Override public void run() {
        throw new RuntimeException();
    }

}
