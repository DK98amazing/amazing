package com.rest;

import java.util.concurrent.Executors;

/**
 * MM.
 *
 * @author liguoyao
 */
public class MM {
    public static void main(String args[]) {
        boolean ss = false;
        Executors.newSingleThreadExecutor().submit(() -> {
            while (!ss) {
                try {
                    System.err.print("2121");
                    throw new RuntimeException();
                } catch (Exception e) {
                    return;
                }
            }
            System.err.print("1231321");
        });
    }
}
