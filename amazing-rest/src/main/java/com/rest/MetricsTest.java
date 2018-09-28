package com.rest;

import com.codahale.metrics.*;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MetricsTest {
    public static Queue<String> q = new LinkedList<String>();
    public static Random random = new Random();
    public static void main(String args[]) {
//        MetricRegistry registry = new MetricRegistry();
//        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
//        CsvReporter csvReporter = CsvReporter.forRegistry(registry).build(new File("E:"));
//        csvReporter.start(3, TimeUnit.SECONDS);
//        reporter.start(5, TimeUnit.SECONDS);
//        registry.register(MetricRegistry.name(MetricsTest.class, "queue", "size"),
//            new Gauge<Integer>() {
//                public Integer getValue() {
//                    return q.size();
//                }
//            });
//        while(true){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            q.add("Job-xxx");
//        }
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);
        Timer timer = registry.timer(MetricRegistry.name(MetricsTest.class,"get-latency"));
        Timer.Context ctx;
        while(true){
            ctx = timer.time();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.stop();
        }
    }
}
