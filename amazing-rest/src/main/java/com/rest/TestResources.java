package com.rest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TestResources.
 *
 * @author liguoyao
 */
public class TestResources {
    public static void main(String args[]) {
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(50000);
        ExecutorService service = new ThreadPoolExecutor(15, 30, 0L, TimeUnit.MILLISECONDS, arrayBlockingQueue, new RejectedExecutionHandler() {
            @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        for (; ; ) {
            service.submit(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
