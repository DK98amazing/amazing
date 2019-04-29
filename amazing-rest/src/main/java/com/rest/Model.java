package com.rest;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Model.
 *
 * @author liguoyao
 */
public class Model implements Comparable<Integer>, Comparator<Integer> {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Download");
        String pa = "^a.\\S$";
        String phone = "^1{1}[0-9]{10}";
        Pattern p = Pattern.compile(phone);
        System.err.println(p.matcher("15957194307").matches());
        Pattern pattern = Pattern.compile(pa);
        String[] list;
        list = file.list((dir, name) -> pattern.matcher(name).matches());
        System.err.println(Arrays.asList(list));

        File file2 = new File("D:\\Download\\112233.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
        StringBuffer stringBuffer = new StringBuffer();
        String ss;
        while (null != (ss = bufferedReader.readLine())) {
            System.err.println(ss);
            stringBuffer.append(ss);
        }

        DataInputStream in = new DataInputStream(new StringInputStream(stringBuffer.toString()));
        while (true) {
            try {
                String ff = in.readLine();
                if (null == ff) {
                    break;
                }
                System.err.println(ff);
            } catch (EOFException e) {
                break;
            }
        }

        Multimap<String, String> neOpList = HashMultimap.create();
        System.err.println(neOpList.keySet());

        String key = "IfIpAddrCfgKey";
        int d = key.indexOf("Key");
        System.err.println(key.substring(0, d));
    }

    @Override
    public int compareTo(Integer o) {
        return 0;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return 0;
    }
}
