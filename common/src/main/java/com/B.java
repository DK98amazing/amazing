package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class B {
    private static List<String> loadProperties() {
        List<String> specs = new ArrayList<>();
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        InputStream in = null;
        try {
            in = B.class.getResourceAsStream("/specification.properties");
            properties.load(in);
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                specs.add(properties.getProperty(key));
            }
        } catch (FileNotFoundException e) {
            System.out.println("ww");
        } catch (IOException e) {
        } finally {
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return specs;
    }

    public static void main(String args[]) {
        System.out.println(loadProperties());
    }
}
