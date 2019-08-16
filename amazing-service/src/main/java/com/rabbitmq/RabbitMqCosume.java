package com.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitMqCosume {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitUtil.getConnection();
            if (null != connection) {
                Channel channel = RabbitUtil.getChannel();
                channel.basicConsume(RabbitUtil.getQueueName(), false, "consumer-Tag", new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        System.err.println(Thread.currentThread().getName());
                        String routingKey = envelope.getRoutingKey(); // 队列名称
                        String contentType = properties.getContentType(); // 内容类型
                        String content = new String(body, StandardCharsets.UTF_8); // 消息正文
                        System.out.println("消费者TAG: " + consumerTag + " 队列： " + routingKey + " 消息正文：" + content);
                        if (System.currentTimeMillis() % 4 == 0) {
                            System.err.println("拒绝消息");
                            channel.basicReject(envelope.getDeliveryTag(), false);
                        } else {
                            channel.basicAck(envelope.getDeliveryTag(), false); // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
