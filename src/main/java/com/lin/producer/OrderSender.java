package com.lin.producer;

import com.lin.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lkmc2
 * @date 2018/9/20
 * @description 订单发送器
 */
@Component
public class OrderSender {

    // rabbit模板引擎
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Order order) throws Exception {

        // 消息唯一标识对象
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        // 将对象转换成消息并发送
        rabbitTemplate.convertAndSend(
                "order-exchange", // exchange交换机key
                "order.abcd", // routingKey路由
                order, // 消息体内容
                correlationData // 消息唯一标识对象
        );
    }

}
