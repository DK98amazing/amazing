package com.amazing.test;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

/**
 * TestTemp.
 *
 * @author liguoyao
 */
public class TestTemp {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.0.11.134", 6379);
    }

    private static void get() {
        throw new RuntimeException();
    }
}
