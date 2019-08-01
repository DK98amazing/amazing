package com.amazing.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * TestTemp.
 *
 * @author liguoyao
 */
public class TestTemp {
    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }

    private static void get() {
        throw new RuntimeException();
    }
}
