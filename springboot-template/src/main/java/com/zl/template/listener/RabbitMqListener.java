package com.zl.template.listener;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMqListener {

    /**
     * 实现一个死信队列
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "dlx_queue", durable = "true"),//死信队列
            exchange = @Exchange(value = "dlx_exchange", type = ExchangeTypes.DIRECT),
            key = "dlx_routing_key"
            )
    )
    public void dlxMessage(String msg) {
        System.out.println("收到死信消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "confirm.queue", durable = "true",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = "dlx_exchange"),
                            @Argument(name = "x-dead-letter-routing-key", value = "dlx_routing_key"),
                            @Argument(name = "x-queue-mode", value = "lazy")}),//开启队列的懒加载模式
            exchange = @Exchange(name = "confirm.topic", delayed = "true", type = ExchangeTypes.TOPIC),
            key = "confirm.*"
    ))
    public void delayMessage(String msg) {
        System.out.println("消息过来了" + msg);
//        throw new RuntimeException("消费失败，消息进入死信队列");
    }



}
