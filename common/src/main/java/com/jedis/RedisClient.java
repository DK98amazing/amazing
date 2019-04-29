package com.jedis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * RedisClient.
 *
 * @author liguoyao
 */
public class RedisClient {
    public static void main(String args[]) throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6380);
        System.err.println(jedis.get("testKey22"));
        jedis.set("testKey22", "testValue22");
        System.err.println(jedis.get("testKey22"));
        TimeUnit.SECONDS.sleep(2);
    }
}
