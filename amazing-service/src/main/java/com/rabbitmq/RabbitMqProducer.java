package com.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMqProducer {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitUtil.getConnection();
            if (null != connection) {
                Channel channel = RabbitUtil.getChannel();
                String content = String.format("当前时间：%s", new Date().getTime());
                byte[] content2Encoder = Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));
                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleAck(long l, boolean b) throws IOException {
                        System.out.println("publish 消息数： " + RabbitUtil.metrics.getPublishedMessages().getCount());
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
                    channel.basicPublish(RabbitUtil.getExchangeName(), RabbitUtil.getRouteKey(), MessageProperties.PERSISTENT_TEXT_PLAIN, content2Encoder);
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
