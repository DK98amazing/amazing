package com.rest;

import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * MM.
 *
 * @author liguoyao
 */
public class MM {
    public static void main(String args[]) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(500);
                HashMap<String, String> map = new HashMap<>();
//                map.put("ff", "111111");
                map.compute("ff", new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) {
                        return "22222";
                    }
                });
                System.out.println(map.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("222");

        });
        t1.start();
        t2.start();
    }
}
