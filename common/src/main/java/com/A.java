package com;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class A {
    public static Object parse(String dir, String filename) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(dir + File.separator + filename);
            NodeList rootList = doc.getElementsByTagName("root");
            for (int i = 0; i < rootList.getLength(); i++) {
                Node root = rootList.item(i);
                NodeList specList = root.getChildNodes();
                for (int j = 0; j < specList.getLength(); j++) {
                    if (j % 2 != 0) {
                        Node spec = specList.item(j);
                        if (!"#comment".equals(spec.getNodeName())) {
                            System.err.println(
                                spec.getAttributes().item(0).getNodeValue() + "." + spec.getAttributes().item(1)
                                    .getNodeValue());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object();
    }

    public static void main(String args[]) throws IOException, ExecutionException, InterruptedException {
        //        parse("E:\\plugin\\HZ08900_view\\Qx-Driver-gyli\\runtime-requirement\\spec\\tn703", "Roots.xml");
        //        File file = new File("E:\\plugin\\HZ08900_view\\Qx-Driver-gyli\\runtime-requirement\\spec\\tn703\\Roots.xml");
        ////        System.err.println(file.exists());
        //        ArrayList<String> list = new ArrayList<>();
        //        list.add("1");list.add("2");list.add("3");list.add("4");
        //        System.err.println(check(list).size());
        //        LoadingCache<Integer, Integer> loadingCache =
        //            CacheBuilder.newBuilder().build(new CacheLoader<Integer, Integer>() {
        //                @Override
        //                public Integer load(Integer key) {
        //                    return key + 1;
        //                }
        //            });
        //        for (int i = 0; ; i++) {
        //            loadingCache.put(i, i + 1);
        //        }
        InputStreamReader reader = new InputStreamReader(new FileInputStream("D:\\cmd.txt"), "UTF-8");
        BufferedReader reader1 = new BufferedReader(reader);
        String result;
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\cmd.txt"));
        writer.write("\n");
        writer.write("append");
        writer.flush();
        while (null != (result = reader1.readLine())) {
            System.err.println(result);
        }

        List<Integer> integers = Lists.newArrayList();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.parallelStream().forEach(System.err::println);

        SettableFuture<Integer> settableFuture = SettableFuture.create();
        ListenableFuture<String> listenableFuture = Futures.transformAsync(settableFuture, input -> {
            SettableFuture<String> stringSettableFuture = SettableFuture.create();
            stringSettableFuture.set(String.valueOf(input));
            return stringSettableFuture;
        });
        TimeUnit.SECONDS.sleep(2);
        settableFuture.set(2);
        System.out.println(listenableFuture.get());
    }

    private static List<String> check(ArrayList<String> rootsVo) {
        Iterator<String> iterator = rootsVo.iterator();
        while (iterator.hasNext()) {
            String tar = iterator.next();
            if (tar.equals("2")) {
                iterator.remove();
            }
        }
        return rootsVo;
    }
}
