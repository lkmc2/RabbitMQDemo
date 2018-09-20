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


    // 回调函数：confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback =  new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();

            if (ack) {
                System.out.println("确认成功，messageId = " + messageId);
                // 编写代码将消息写入数据库日志表
            } else {
                System.out.println("确认出现异常，" + cause);
            }
        }
    };

    /**
     * 发送订单消息给RabbitMQ服务器
     * @param order 订单
     */
    public void send(Order order) throws Exception {
        // 设置确认回调方法
        rabbitTemplate.setConfirmCallback(confirmCallback);

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
