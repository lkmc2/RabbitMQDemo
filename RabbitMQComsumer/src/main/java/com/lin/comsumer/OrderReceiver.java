package com.lin.comsumer;

import com.lin.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * @author lkmc2
 * @date 2018/9/20
 * @description 订单接收器
 */
@Component
public class OrderReceiver {

    /**
     * 从RabbitMQ队列中获取消息
     * RabbitListener 注解配置接听的队列和交换机以及路由关键字的信息
     * RabbitHandler 会自动把收到的消息放到这个方法
     *
     * @param order 订单对象（Payload注解会把接受到的消息体转换成该对象）
     * @param headers 消息头（存储消息的必要信息）
     * @param channel 管道（使用手工签收消息需要这个）
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "order-queue", durable = "true"),
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),
                    key = "order.*"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws IOException {
        // 运行此方法先启动消费者端的ConsumerApplication，
        // 然后启动生产者端的RabbitMQApplicationTests的testSend1方法

        // 消费者操作
        System.out.println("-------收到消息，开始消费-------");
        System.out.println("订单ID:" + order.getId());

        // 从消息头获取消息传递标签
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        // 手工签收该消息
        channel.basicAck(deliveryTag, false);
    }

}
