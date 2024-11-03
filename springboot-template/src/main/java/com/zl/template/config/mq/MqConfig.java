package com.zl.template.config.mq;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@AllArgsConstructor
public class MqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData!= null) {
                String messageId = correlationData.getId();
                if (ack) {
                    System.out.println("消息发送成功，消息ID: " + messageId);
                } else {
                    System.out.println("消息发送失败，消息ID: " + messageId + "，原因: " + cause);
                }
            }
        });
        // 设置ReturnCallback
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("消息无法路由，退回消息：" + new String(String.valueOf(returnedMessage.getMessage())));
            System.out.println("回复码: " + returnedMessage.getReplyCode());
            System.out.println("回复文本: " + returnedMessage.getReplyText());
            System.out.println("交换器: " + returnedMessage.getExchange());
            System.out.println("路由键: " + returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
