package com.jedis;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * RedisClient2.
 *
 * @author liguoyao
 */
public class RedisClient2 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6380);
        System.out.println(jedis.get("testKey"));

        Object jsonObject = JSON.toJSON("{\n" + "    \"input\": {\n" + "        \"host\": \"10.4.48.26\",\n"
            + "        \"ne-Name\": \"10.4.48.26\",\n" + "        \"port\": \"6666\"\n" + "    }\n" + "}");
        System.out.println(jsonObject);
        SettableFuture<Integer> settableFuture = SettableFuture.create();
        Futures.addCallback(
            Futures.withTimeout(settableFuture, 0, TimeUnit.SECONDS, Executors.newSingleThreadScheduledExecutor()),
            new FutureCallback<Integer>() {
                @Override
                public void onSuccess(@Nullable Integer result) {
                    System.out.println(1);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println(2);
                }
            });
    }
}
