package com.rest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static Selector selector = null;
    public static ServerSocketChannel server = null;

    public static void main(String[] args) {
        Server.init();
    }

    public static void init() {
        initSelector();// 初始化selector
        initServerSocketChannel(); // 初始化serverSocketChannel
        run();
    }

    // first
    public static void initSelector() {
        try {
            selector = Selector.open();// 打开selector
        } catch (IOException e) {
            // 初始化selector失败
            e.printStackTrace();
        }
    }

    public static void initServerSocketChannel() {
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(7777), 1024);
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            // 初始化serverSocket失败
            e.printStackTrace();
        }
    }

    public static void run() {
        while (true) {
            try {
                selector.select(1000); // 阻塞selector
                // ================如果有新连接
                Set<SelectionKey> selectedKeys = selector.selectedKeys();// 获得事件集合;
                // ================遍历selectedKeys
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();// 获得到当前的事件
                    // ===============处理事件
                    handle(key);
                    // ===============
                    iterator.remove(); // 移除事件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 初始化seclector和serverSocket
    // 当一个selector上有新的事件有反应后 select();
    // 获得获得事件集合
    // 遍历集合事件
    // 处理事件
    public static void handle(SelectionKey key) {
        try {
            // 连接就绪
            if (key.isAcceptable()) {
                handleAcceptable(key);
            }
            // 读就绪
            if (key.isReadable()) {
                handelReadable(key);
            }
        } catch (IOException e) {
            key.cancel();
            if (key.channel() != null) {
                try {
                    key.channel().close();
                } catch (IOException e1) {
                }
            }
        }
    }

    // 处理读事件
    public static void handelReadable(SelectionKey key) throws IOException {
        // ==================我们要将数据从通道读到buffer里
        SocketChannel ssc = (SocketChannel) key.channel(); // TODO:
        // 为什么这里是socketChannel
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String content = "服务端返回了: ";
        int readBytes = ssc.read(byteBuffer);// channel ==> buffer
        if (readBytes > 0) {// 代表读完毕了,准备写(即打印出来)
            byteBuffer.flip(); // 为write()准备
            // =====取出buffer里的数据
            byte[] bytes = new byte[byteBuffer.remaining()]; // 创建字节数组
            byteBuffer.get(bytes);// 将数据取出放到字节数组里
            content += new String(bytes);
            content += "__________";
            doWrite(ssc, content);
        }
    }

    // 处理连接事件
    public static void handleAcceptable(SelectionKey key) throws IOException {
        // 获得对应的ServerSocketChannel TODO: 这里为什么是socketChannel
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        // 得到对应的SocketChannel TODO:accpet是什么意思
        SocketChannel channel = ssc.accept();// 在非阻塞模式下，accept()可能为null
        // 处理socketChannel
        channel.configureBlocking(false); // TODO: 为什么设置非阻塞
        channel.register(selector, SelectionKey.OP_READ); // TODO: 将准备状态转化为读状态

        // 将key对应Channel设置为准备接受其他请求
        key.interestOps(SelectionKey.OP_ACCEPT);// TODO:
    }

    // ============= 发送消息
    private static void doWrite(SocketChannel sc, String data) throws IOException {
        byte[] req = data.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println(data + "   Send 2 Service successed");
        }
    }
}

