package com.zl.template;

import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemUserMapper;
import com.zl.template.mongodb.po.Comment;
import com.zl.template.service.impl.CommentService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private CommentService commentService;

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
    @Test
    void MongoDbTest1(){
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
    void MongoDbTest2(){
        List<Comment> commentList = commentService.findCommentList();
        System.out.println(commentList);
    }


}
