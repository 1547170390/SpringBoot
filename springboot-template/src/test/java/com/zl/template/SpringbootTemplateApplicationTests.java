package com.zl.template;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void RedisTest() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }
    @Test
    void RabbitMqTest(){
        rabbitTemplate.convertAndSend("test", "world");
    }

}
