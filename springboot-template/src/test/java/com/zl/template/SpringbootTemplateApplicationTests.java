package com.zl.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void RedisTest() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }

}
