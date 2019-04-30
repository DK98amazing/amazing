package com.amazing.test;

/**
 * TestTemp.
 *
 * @author liguoyao
 */
public class TestTemp {
    public static void main(String[] args) {
        try {
            get();
        } catch (Exception e) {
            System.err.println(1);
        }
        System.out.println(2);
    }

    private static void get() {
        throw new RuntimeException();
    }
}
