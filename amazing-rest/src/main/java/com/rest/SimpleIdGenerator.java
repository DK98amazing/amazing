/*
 * Copyright (c) 2018 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.rest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * SimpleIdGenerator.
 *
 * @author liguoyao
 */
public class SimpleIdGenerator {
    private static Map<Class, LongAdder> map = null;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    private static Map<Class, LongAdder> getInstance() {
        if (map == null) {
            lock.writeLock().lock();
            if (map == null) {
                map = new ConcurrentHashMap<>();
            }
            lock.writeLock().unlock();
        }
        return map;
    }

    public static synchronized long getID(Class clazz) {
        LongAdder adder = getInstance().get(clazz);
        if (adder == null) {
            adder = new LongAdder();
            map.put(clazz, adder);
        }
        long ret = adder.sum();
        adder.increment();
        return ret;
    }

    public static void reset(Class clazz) {
        map.remove(clazz);
    }
}
