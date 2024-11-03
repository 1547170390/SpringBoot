package com.zl.template;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zl.template.config.redis.RedisCache;
import com.zl.template.domain.SystemRole;
import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemRoleMapper;
import com.zl.template.mapper.SystemUserMapper;
import com.zl.template.mongodb.po.Comment;
import com.zl.template.service.SystemRoleService;
import com.zl.template.service.impl.CommentService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;


    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemRoleService systemRoleService;


    @Autowired
    private CommentService commentService;

    @Test
    void RedisTest() {
//        stringRedisTemplate.opsForValue().set("hello", "world");
//        SystemUser systemUser = new SystemUser();
//        systemUser.setEmail("zhangsan@qq.com");
//        redisCache.setCacheObject("测试用户",systemUser,50, TimeUnit.SECONDS);
        /**
         * sortedset
         */
        stringRedisTemplate.opsForZSet().add("set","set集合",1);
        stringRedisTemplate.opsForZSet().add("set","第二个腧",2);
    }

    @Test
    void RabbitMqTest() {
        rabbitTemplate.convertAndSend("test", "world");
    }

    @Test
    void RabbitMqConfirmTest() throws InterruptedException {

        //路由器正确，队列错误，应该触发returncallback，收到ack
//        rabbitTemplate.convertAndSend("confirm.topic", "test.confirm", "callback测试");
//        rabbitTemplate.convertAndSend("confirm.topic", "confirm.test", "callback测试");
//        rabbitTemplate.convertAndSend("confirm.topic", "123", "confirm测试");
        //消息id，确保消息不会被重复消费
        CorrelationData correlationData = new CorrelationData("1");
//        rabbitTemplate.convertAndSend("confirm.topic", "confirm.test", "消费者确认",correlationData);
        //发送一个有过期时间的消息
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration(String.valueOf(5000));
//        Message message = new Message("消息".getBytes(), messageProperties);
//        rabbitTemplate.convertAndSend("zl.topic", "time", message,correlationData);

        //发送一个延迟的消息

        rabbitTemplate.convertAndSend("confirm.topic", "confirm.test", "延迟消息", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelayLong(5000L);
                return message;
            }
        });
    }


    @Test
    void MybaticPlusTest() {
//        LambdaQueryWrapper<SystemRole> wrapper = new LambdaQueryWrapper<>();
//        wrapper.le(SystemRole::getRoleId,1110);
////        systemRoleMapper.selectList(wrapper).forEach(System.out::println);
//
//        List<SystemRole> list = systemRoleService.list(wrapper);
//        for (SystemRole systemRole : list) {
//            System.out.println(systemRole);
//        }

        systemRoleService.lambdaQuery().le(SystemRole::getRoleId,1110).list().forEach(System.out::println);


    }

    @Test
    void MongoDbTest1() {
        /**
         * 保存一个评论
         */
        Comment comment = new Comment();
        comment.setArticleid("100000");
        comment.setContent("保存一个评论");
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1003");
        comment.setNickname("凯撒大帝");
        comment.setState("1");
        comment.setLikenum(0);
        comment.setReplynum(0);

        commentService.saveComment(comment);
    }

    @Test
    void MongoDbTest2() {
        List<Comment> commentList = commentService.findCommentList();
        System.out.println(commentList);
    }


}
