package com.amazing.test;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;

/**
 * TestEventBus.
 *
 * @author liguoyao
 */
public class TestEventBus {
    public static void main(String[] args) {
        EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
        EventBus eventBus2 = new EventBus("another");
        TestEventBus testEventBus = new TestEventBus();
        eventBus.register(testEventBus);
        eventBus2.register(testEventBus);
        System.out.println(Thread.currentThread().getName());
        eventBus.post(new StringBuffer("test eventbus"));
        eventBus.unregister(testEventBus);
        eventBus2.post(new StringBuffer("test eventbus"));
    }

    @Subscribe
    public void doSubscribe(Object object) {
        System.out.println(Thread.currentThread().getName());
        System.err.println(object.getClass() + " : " + object);
    }
}
