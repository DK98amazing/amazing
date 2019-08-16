package com.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtil {
    private static volatile Connection connection = null;
    private static Object lock = new Object();
    private static String exchangeName = "test_exchange";
    private static String queueName = "test_queue";
    private static String routeKey = "test_routeKey";

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
}
