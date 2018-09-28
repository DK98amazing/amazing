package com.rest;

import java.util.concurrent.TimeUnit;

public class TestRangeSet {
    public static void main(String args[]) {
        final TestRangeSet rangeSet = new TestRangeSet();
        int i = 0;
        new Thread(rangeSet::test).start();
    }
    public void test() {
        synchronized (TestRangeSet.class) {
            try {
                while (true)
                TimeUnit.MINUTES.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
