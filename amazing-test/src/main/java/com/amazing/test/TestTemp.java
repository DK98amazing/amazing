package com.amazing.test;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.xerial.snappy.Snappy;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * TestTemp.
 *
 * @author liguoyao
 */
public class TestTemp {
    public static void main(String[] args) throws IOException {
//        System.out.println(InetAddress.getLocalHost().getHostAddress());
//        System.err.println("212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212".getBytes().length);
//        byte[] bytes = Snappy.compress("212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212212adadd@!#!#!#!d121212", "UTF-8");
//        System.err.println(bytes.length);
//        System.err.println(Snappy.uncompressedLength(bytes));
//
//        String sss = "https://10.0.10.100/dqsdsadas/dada";
//        System.err.println(sss.indexOf(":", 6));
//        System.err.println(sss.substring(0, sss.indexOf("/", 8)) + ":80" + sss.substring(sss.indexOf("/", 8)));

//        URL url = new URL("http://www.baidu.com");
//        System.out.println(url.getHost());
//        System.out.println(url.getPort());
//        System.out.println(url.getProtocol());
//        System.out.println(url.getPath());
//
//        URLConnection urlConnection = url.openConnection();
//        urlConnection.setDoOutput(true);
//        urlConnection.setDoInput(true);
//        urlConnection.connect();
//        OutputStream outputStream = urlConnection.getOutputStream();
//        byte[] bytes = new byte[1024];
//        outputStream.write("菜鸟教程".getBytes());
//        outputStream.flush();
//        outputStream.close();
//        InputStream inputStream = urlConnection.getInputStream();
//        byte[] b=new byte[1024];
//        int len;
//        while((len=inputStream.read(b))!=-1)
//        {
//            System.out.println(new String(b,0,len));
//        }
//        inputStream.close();

        String sss = "http://10.0.11.134:8080/webbot/servlet/DownLoadServlet?downFile=http://10.0.11.134:8080/recorderfileserver/resources/weixin_shortvideo_path/2019_08_13/LabDTL_IZHizWcSpikIPWeF8tkCwijutlEU15Nl-jxoOkxKD_9V3zzrfwuKAYy3o.mp4&contentType=14";
        int ddd = sss.indexOf("contentType");
        String url = sss.substring(0, ddd - 1);
        int ddd2 = url.lastIndexOf(".");
        System.out.println(sss.substring(0, ddd2 + 1) + "ogg" + sss.substring(ddd-1));

        System.err.println("-----------------------------------------------------");
        List<String> params = new ArrayList<String>();
        List<Object> paramValues = new ArrayList<Object>();
//        params.add("emailAddress");
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
//        paramValues.add(list);
        System.err.println(params.size());
//        System.err.println((Collection)paramValues.toArray(new Object[0])[0]);

//        String ivrUri = "sip:IVRBE@10.0.12.89:5090;voicexml=http://10.0.12.89:8070/external/zrg";
//        System.err.println(ivrUri.substring(ivrUri.lastIndexOf('/')+1));
//        System.err.println(UUID.randomUUID().toString().replaceAll("-", "").getBytes().length);

        String ssss = new BASE64Encoder().encode("http://10.0.12.61:8080/recorderfileserver/resources/weixin_shortvideo_path/2019_08_28".getBytes()).replace("\r", "").replace("\n", "");
        System.err.println(ssss);
        System.err.println(new String(new BASE64Decoder().decodeBuffer("aHR0cDovLzEwLjAuMTIuNjE6ODA4MC9yZWNvcmRlcmZpbGVzZXJ2ZXIvcmVzb3VyY2VzL3dlaXhpbl9zaG9ydHZpZGVvX3BhdGgvMjAxOV8wOF8yOA==")));

        MetricRegistry metricRegistry = new MetricRegistry();
        Map<Date, Integer> map = new HashMap<>();
        AtomicInteger i = new AtomicInteger();
        Gauge gauge = metricRegistry.gauge("testGuage", () -> () -> {map.put(new Date(), i.getAndIncrement());return map;});
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
        new Thread(() -> {
            while (true){}
        }).start();
    }

    private static void get() {
        throw new RuntimeException();
    }
}
