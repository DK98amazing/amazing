package com.rabbitmq;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.CredentialsProvider;
import com.rabbitmq.client.impl.StandardMetricsCollector;
import com.rabbitmq.client.impl.nio.NioParams;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitUtil {
    private static volatile Connection connection = null;
    private static Object lock = new Object();
    private static String exchangeName = "test_exchange";
    private static String queueName = "test_queue";
    private static String routeKey = "test_routeKey";
    private static MetricRegistry registry = new MetricRegistry();
    public static StandardMetricsCollector metrics = new StandardMetricsCollector(registry);

    public static String getRouteKey() {
        return routeKey;
    }

    public static String getExchangeName() {
        return exchangeName;
    }

    public static String getQueueName() {
        return queueName;
    }

    public static Connection getConnection() {
        if (null == connection) {
            synchronized (lock) {
                if (null == connection) {
                    ConnectionFactory connectionFactory = new ConnectionFactory();
                    connectionFactory.setHost("127.0.0.1");
                    connectionFactory.setPort(5672);
                    connectionFactory.setUsername("guest");
                    connectionFactory.setPassword("guest");
                    connectionFactory.setVirtualHost("/");
                    connectionFactory.setAutomaticRecoveryEnabled(true);
                    connectionFactory.setNetworkRecoveryInterval(10000);
                    connectionFactory.setConnectionTimeout(1000);
                    connectionFactory.setCredentialsProvider(new CredentialsProvider() {
                        @Override
                        public String getUsername() {
                            return "guest";
                        }

                        @Override
                        public String getPassword() {
                            return "guest";
                        }
                    });
                    connectionFactory.setHeartbeatExecutor(Executors.newScheduledThreadPool(1, new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r, "Heartbeat Thread");
                        }
                    }));
                    //FIXME:need com/codahale/metrics/MetricRegistry
                    connectionFactory.setMetricsCollector(metrics);
                    ConsoleReporter reporter = ConsoleReporter
                            .forRegistry(registry)
                            .convertDurationsTo(TimeUnit.MILLISECONDS)
                            .build();
                    reporter.start(10, TimeUnit.SECONDS);
                    connectionFactory.useNio();
                    connectionFactory.setNioParams(new NioParams().setNbIoThreads(2));
                    connectionFactory.setThreadFactory(r -> new Thread(r,"Consumer Thread"));
                    try {
                        connection = connectionFactory.newConnection();
                    } catch (IOException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static Channel getChannel() {
        Channel channel = null;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(RabbitUtil.getQueueName(), true, false, false, null);
            channel.exchangeDeclare(RabbitUtil.getExchangeName(), BuiltinExchangeType.DIRECT, true);
            channel.queueBind(RabbitUtil.getQueueName(), RabbitUtil.getExchangeName(), RabbitUtil.getRouteKey());
            channel.confirmSelect();
            channel.basicQos(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
