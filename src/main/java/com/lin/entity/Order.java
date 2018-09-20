package com.lin.entity;

import java.io.Serializable;

/**
 * @author lkmc2
 * @date 2018/9/20
 * @description 订单
 */
public class Order implements Serializable {

    private String id;

    private String name;

    // 消息发送的唯一id
    private String messageId;

    public Order() {
    }

    public Order(String id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
