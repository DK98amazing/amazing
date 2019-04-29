/*
 * Copyright (c) 2018 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package designmodel;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class Test {

    static ScheduledFuture<?> scheduleAtFixedRate = null;
    static Map<String, Object> scheduledMap = new HashMap<String, Object>();

    public static void main(String[] args) throws ParseException, InterruptedException {
        test1();
        test3();
    }


    private static void test3() throws InterruptedException {
        final ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);


        newScheduledThreadPool.schedule(() -> {
            System.out.println("333333333333333333333333" + new Date());
            // ScheduledFuture<?> scheduleAtFixedRate = (ScheduledFuture<?>) scheduledMap.get("test1");
            // scheduleAtFixedRate.cancel(false);
            // newScheduledThreadPool.shutdown();
            ScheduledExecutorService exe = (ScheduledExecutorService) scheduledMap.get("test2");
            exe.shutdown();
            newScheduledThreadPool.shutdown();
            //                newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            //
            //                    @Override public void run() {
            //                        System.out.println("444444444444444444");
            //
            //                    }
            //                }, 0, 1, TimeUnit.SECONDS);
        }, 20, TimeUnit.SECONDS);


    }

    private static void test1() {
        final ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
        new Thread(() -> {
            System.out.println("111111111111111111111111" + new Date());

            scheduleAtFixedRate = exe.scheduleWithFixedDelay(new Runnable() {

                @Override public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(111111);

                }
            }, 0, 3, TimeUnit.SECONDS);
            scheduleAtFixedRate.cancel(true);

            scheduledMap.put("test1", scheduleAtFixedRate);
            ;
            scheduledMap.put("test2", exe);
        }).start();
    }
}
