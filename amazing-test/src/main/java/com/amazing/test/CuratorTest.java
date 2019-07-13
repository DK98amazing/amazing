package com.amazing.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorTest {
    public static void main(String[] args) throws Exception {
        final CountDownLatch cdl = new CountDownLatch(2);
        ExecutorService es = Executors.newFixedThreadPool(2);

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181, 127.0.0.1:2182, 127.0.0.1:2183").connectionTimeoutMs(3000).retryPolicy(retryPolicy).build();
        curatorFramework.start();
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/test", "testValue".getBytes());
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.err.println("event code: " + curatorEvent.getResultCode() + ", type: " + curatorEvent.getType());
                cdl.countDown();
            }
        }, es).forPath("/test/1", "testValue1".getBytes());
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.err.println("event code: " + curatorEvent.getResultCode() + ", type: " + curatorEvent.getType());
                cdl.countDown();
            }
        }, es).forPath("/test/2", "testValue2".getBytes());

        cdl.await();
        es.shutdown();

        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/test");
        System.err.println("Current Data: " + stat.getVersion());
        int version = curatorFramework.setData().withVersion(0).forPath("/test", "123".getBytes()).getVersion();
        System.err.println("Update data: " + version);

        /**
         * Curator事件监听方式
         *  1. 利用Watcher对节点进行监听。 client.getData().usingWatcher()
         *  2. CuratorListener监听 针对background通知和错误通知。对于节点的创建或修改则不会触发监听事件
         *  3. Curator引入了Cache来实现对Zookeeper服务端事件监听
         *      final NodeCache nodeCache = new NodeCache(client,path);
         *         nodeCache.start();
         *         nodeCache.getListenable().addListener(new NodeCacheListener(){});
         */
        final NodeCache nc = new NodeCache(curatorFramework, "/test", false);
        nc.start();
        //通过回调函数监听事件
        nc.getListenable().addListener(new NodeCacheListener() {

            public void nodeChanged() throws Exception {
                System.out.println("update--current data: " + new String(nc.getCurrentData().getData()));
            }
        });

        curatorFramework.setData().forPath("/test", "test123".getBytes());
        Thread.sleep(200);
        curatorFramework.setData().forPath("/test", "test123ee".getBytes());
        Thread.sleep(1000);
//        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/test");
        Thread.sleep(1000);
        nc.close();

        Stat stat2 = new Stat();
        curatorFramework.getData().storingStatIn(stat2).forPath("/test");
        curatorFramework.delete().deletingChildrenIfNeeded().withVersion(stat2.getVersion()).forPath("/test");

        List<String> paths = curatorFramework.getChildren().forPath("/");
        for (String path : paths) {
            if ("zookeeper".equals(path)) {
                continue;
            }
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/" + path);
        }
    }
}