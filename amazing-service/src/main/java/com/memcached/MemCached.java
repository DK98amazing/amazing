package com.memcached;

import net.rubyeye.xmemcached.KeyProvider;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class MemCached {
    public static MemcachedClient getMemcachedClient() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 11211);
        MemcachedClient memcachedClient = null;
        try {
            XMemcachedClientBuilder builder = new XMemcachedClientBuilder(Arrays.asList(address));
            builder.setKeyProvider(new KeyProvider() {
                @Override
                public String process(String s) {
                    return "key-" + s;
                }
            });
            memcachedClient = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memcachedClient;
    }
}
