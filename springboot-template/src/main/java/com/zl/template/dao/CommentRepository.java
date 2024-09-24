package com.zl.template.dao;

import com.zl.template.mongodb.po.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

//评论的持久层接口
public interface CommentRepository extends MongoRepository<Comment, String> {

}
