package com;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FileTest {
    public static void getModifiedTime_2() {
        File f = new File("E:\\ttt");
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        System.out.println("修改时间 " + formatter.format(cal.getTime()));
        //输出：修改时间[2]    2009-08-17 10:32:38
    }

    public static void main(String args[]) {
                getModifiedTime_2();
        try {
            DigestUtils.md5Hex(new FileInputStream("E:\\ttt\\ttts.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkDirChanged("E:/ttt");
    }

    public static void checkDirChanged(String _dir) {
        try {
            //得到默认的WatchService服务
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(_dir);
            //为Path注册key,可选择的Kind<?> ENTAY_DELETE，ENTRY_CREATE,ENTRY_MODIFY 等
            //将Path注册在变化检测名单中
            dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);

            new Thread(() -> {
                try {
                    WatchKey key;
                    while ((key = watcher.take()) != null) {

                        //一旦Watchkey到来，WatchEvent遍历
                        List<WatchEvent<?>> events = key.pollEvents();
                        for (WatchEvent<?> event : events) {
                            //一旦Kind<?>类型为ENTRY_MODIFY，打印Home dir change
//                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                System.out.println(
                                    "Home dir changed " + event.context() + "  " + event.kind() + "   " + event
                                        .count());
//                            }
                        }
                        if (!key.reset()) {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }

            }).start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    watcher.close();
                    System.out.println(12312421);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
        }
    }

}
