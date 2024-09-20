package com.zl.template;

import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemUserMapper;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.crypto.SecretKey;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Test
    void RedisTest() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }
    @Test
    void RabbitMqTest(){
        rabbitTemplate.convertAndSend("test", "world");
    }
    @Test
    void MybaticPlusTest(){
        SystemUser systemUser = systemUserMapper.selectById("18107");
        System.out.println(systemUser);
    }


}
