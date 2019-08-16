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
                Channel channel = connection.createChannel();
                channel.queueDeclare(RabbitUtil.getQueueName(), true, false, false, null);
                channel.exchangeDeclare(RabbitUtil.getExchangeName(), BuiltinExchangeType.DIRECT, true);
                channel.queueBind(RabbitUtil.getQueueName(), RabbitUtil.getExchangeName(), RabbitUtil.getRouteKey());
                channel.basicConsume(RabbitUtil.getQueueName(), false, RabbitUtil.getRouteKey(), new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey(); // 队列名称
                        String contentType = properties.getContentType(); // 内容类型
                        String content = new String(body, StandardCharsets.UTF_8); // 消息正文
                        System.out.println("队列： " + routingKey + " 消息正文：" + content);
                        channel.basicAck(envelope.getDeliveryTag(), false); // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
