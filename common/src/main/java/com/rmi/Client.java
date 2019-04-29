package com.rmi;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.File;

/**
 * Client.
 *
 * @author liguoyao
 */
public class Client {
    public static void main(String args[]) {
        Multimap<String, String> multiMap = HashMultimap.create();
        multiMap.put("1", "1");
        multiMap.put("1", "2");
        System.out.println(multiMap.get("1"));
        //        Preconditions.checkNotNull(null);
        System.out.println(2222);

        File file = new File("E:\\plugin\\HZ08900_view\\Qx-Driver-gyli\\threads_report.txt");
        File file1 = new File(file.getParent());
        System.out.println(file1.exists());
        System.out.println(file1.mkdirs());
    }
}
