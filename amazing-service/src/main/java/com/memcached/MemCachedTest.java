package com.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;

public class MemCachedTest {
    public static void main(String[] args) {
        MemcachedClient client = MemCachedUtil.getMemcachedClient();
        try {
            client.add("test", 0, "testValue", 3000L);
            Thread.sleep(2000);
            System.err.println((String) client.get("test"));
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
    }
}
