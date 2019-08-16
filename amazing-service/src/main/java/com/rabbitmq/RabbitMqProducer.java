package com.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMqProducer {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitUtil.getConnection();
            if (null != connection) {
                Channel channel = connection.createChannel();
                channel.queueDeclare(RabbitUtil.getQueueName(), true, false, false, null);
                channel.exchangeDeclare(RabbitUtil.getExchangeName(), BuiltinExchangeType.DIRECT, true);
                System.err.println("queue: " + RabbitUtil.getQueueName());
                channel.queueBind(RabbitUtil.getQueueName(), RabbitUtil.getExchangeName(), RabbitUtil.getRouteKey());
                channel.confirmSelect();
                channel.basicQos(5);
                String content = String.format("当前时间：%s", new Date().getTime());
                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleAck(long l, boolean b) throws IOException {
                        System.out.println("l: " + l + " ; b: " + b);
                    }

                    @Override
                    public void handleNack(long l, boolean b) throws IOException {
                        System.out.println("l: " + l + " ; b: " + b);
                    }
                });
                channel.addConfirmListener((l, b) -> System.err.println(String.valueOf(l) + String.valueOf(b)), (l, b) -> System.err.println(String.valueOf(l) + String.valueOf(b)));
                channel.addReturnListener(new ReturnCallback() {
                    @Override
                    public void handle(Return aReturn) {
                        System.err.println(aReturn.getBody());
                    }
                });
                channel.addReturnListener((i, s, s1, s2, basicProperties, bytes) -> System.err.println(s + s1 + s2 + bytes));
                int i = 0;
                while (i < 10000) {
                    channel.basicPublish(RabbitUtil.getExchangeName(), RabbitUtil.getRouteKey(), MessageProperties.PERSISTENT_TEXT_PLAIN, content.getBytes("UTF-8"));
                    TimeUnit.SECONDS.sleep(5);
                    i++;
                }
                channel.close();
                connection.close();
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
