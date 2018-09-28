package com.rest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {

    public static void main(String[] args) {
        Client.init();
    }

    //  public ArrayBlockingQueue<String> arrayQueue = new ArrayBlockingQueue<String>(8);
    //  static Charset charset = Charset.forName("UTF-8");
    private static Selector selector = null;
    private volatile static boolean stop = false;
    private static SocketChannel channel = null;

    public static void init() {
        initSelector();// 初始化selector
        initSocketChannel(); // 初始化serverSocketChannel
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                channel.socket().close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        run();
    }

    // 初始化selector
    public static void initSelector() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 初始化SocketChannel
    public static void initSocketChannel() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("127.0.0.1", 7777));
            channel.register(selector, SelectionKey.OP_CONNECT);
//            channel.register(selector, SelectionKey.OP_READ);
//            channel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            System.out.println("client: 失去主机连接");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run() {
        try {
            while (!stop) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    handle(key);
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void handle(SelectionKey key) throws IOException {
        // 连接就绪
        try {
            if (key.isConnectable()) {
                handleConnectable(key);
            }
            // 读就绪
            if (key.isReadable()) {
                handelReadable(key);
            }
        } catch (Exception e) {
            key.cancel();
            System.out.println("Server closed");
            if (key.channel() != null) {
                try {
                    key.channel().close();
                } catch (IOException e1) {
                }
            }
        }
    }

    private static void handelReadable(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int temp = sc.read(buffer); // 从channel读到buffer
        System.out.println(temp);
        String content = "来自服务端的: ";
        if (temp > 0) {// 代表读完毕了,准备写(即打印出来)
            buffer.flip(); // 为write()准备
            // =====取出buffer里的数据
            byte[] bytes = new byte[buffer.remaining()]; // 创建字节数组
            buffer.get(bytes);// 将数据取出放到字节数组里
            content += new String(bytes);
            content += "============";
            System.out.println(content);
            // doWrite(sc, content);
        }
        // key.interestOps(SelectionKey.OP_READ);// TODO:
    }

    private static void handleConnectable(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        if (sc.finishConnect()) {
            // 将关注的事件变成read
            sc.register(selector, SelectionKey.OP_READ);
            doWrite(sc, "dddddd");
        }
    }

    private static void doWrite(SocketChannel sc, String data) throws IOException {
        byte[] req = data.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(req);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
        // if (!byteBuffer.hasRemaining()) {
        // System.out.println("Send successed : " + data);
        // }
    }
}

