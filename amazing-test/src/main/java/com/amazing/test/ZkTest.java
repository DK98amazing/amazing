package com.amazing.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class ZkTest implements Watcher {
    private static final CountDownLatch cd1 = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZkTest());
        System.err.println(zooKeeper.getState());
        try {
            cd1.await();
//            zooKeeper.create("/lockqq", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.getChildren("/lockqq", true, new Stat());
            zooKeeper.create("/lockqq/sub", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            zooKeeper.create("/lockqq/sub", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            zooKeeper.create("/lockqq/sub", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            Collections.sort(zooKeeper.getChildren("/lockqq", false));
            System.err.println(zooKeeper.getChildren("/lockqq", false));
        } catch (InterruptedException e) {
            System.out.println("ZK Session established.");
        } catch (KeeperException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < 100; i++) { // 开启100个线程
//            //模拟分布式锁的场景
//            new Thread(new OrderService()).start();
//        }
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event: " + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            cd1.countDown();
            if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                System.err.println(watchedEvent.getPath() + " created");
            }
        } else {

        }
    }

    public interface ExtLock {
        void getLock();

        void unLock();
    }

    public abstract static class ZookeeperAbstractLock implements ExtLock {
        private static final String CONNECTION = "127.0.0.1:2181";
        protected ZkClient zkClient = new ZkClient(CONNECTION);
        protected String lockPath = "/lockPath";

        //获取锁
        public void getLock() {
            //1、连接zkClient 创建一个/lock的临时节点
            // 2、 如果节点创建成果，直接执行业务逻辑，如果节点创建失败，进行等待
            if (tryLock()) {
                System.out.println("#####成功获取锁######");
            } else {
                //进行等待
                waitLock();
            }

            //3、使用事件通知监听该节点是否被删除    ，如果是，重新进入获取锁的资源

        }

        //创建失败 进行等待
        abstract void waitLock();


        abstract boolean tryLock();


        //释放锁
        public void unLock() {
            //执行完毕 直接连接
            if (zkClient != null) {
                zkClient.close();
                System.out.println("######释放锁完毕######");
            }

        }
    }

    public static class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {
        private CountDownLatch countDownLatch;

        void waitLock() {
            IZkDataListener iZkDataListener = new IZkDataListener() {

                // 节点被删除
                public void handleDataDeleted(String arg0) throws Exception {
                    if (countDownLatch != null) {
                        countDownLatch.countDown(); // 计数器为0的情况，await 后面的继续执行
                    }

                }

                // 节点被修改
                public void handleDataChange(String arg0, Object arg1) throws Exception {

                }
            };

            // 监听事件通知
            zkClient.subscribeDataChanges(lockPath, iZkDataListener);
            // 控制程序的等待
            if (zkClient.exists(lockPath)) {  //如果 检查出 已经被创建了 就new 然后进行等待
                countDownLatch = new CountDownLatch(1);
                try {
                    countDownLatch.wait(); //等待时候 就不往下走了   当为0 时候 后面的继续执行
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            //后面代码继续执行
            //为了不影响程序的执行 建议删除该事件监听 监听完了就删除掉
            zkClient.unsubscribeDataChanges(lockPath, iZkDataListener);
        }

        boolean tryLock() {
            try {
                zkClient.createEphemeral(lockPath);
//            System.out.println("#########获取锁######");
                return true;
            } catch (Exception e) {
                // 如果失败 直接catch
                return false;
            }
        }
    }

    public static class OrderNumGenerator {
        //区分不同的订单号
        private static int count = 0;

        //单台服务器，多个线程 同事生成订单号
        public String getNumber() {
            try {
                Thread.sleep(500);
            } catch (Exception e) {

            }
            SimpleDateFormat simpt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            return simpt.format(new Date()) + "-" + ++count;  //时间戳后面加了 count

        }

    }

    public static class OrderService implements Runnable {

        private OrderNumGenerator orderNumGenerator = new OrderNumGenerator(); // 定义成全局的
        private ExtLock lock = new ZookeeperDistrbuteLock();

        public void run() {
            getNumber();
        }

        public synchronized void getNumber() { // 加锁 保证线程安全问题 让一个线程操作
            try {
                lock.getLock();
                String number = orderNumGenerator.getNumber();
                System.err.println(Thread.currentThread().getName() + ",number" + number);

            } catch (Exception e) {

            } finally {
                lock.unLock();
            }
        }

    }
}
