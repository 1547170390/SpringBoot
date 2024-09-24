package com.zl.template.service.impl;

import com.zl.template.dao.CommentRepository;
import com.zl.template.mongodb.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//评论的业务层
@Service
public class CommentService {
    //注入dao
    @Autowired
    private CommentRepository commentRepository;
    /**
     * 保存一个评论
     */
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
    /**
     * 更新评论
     */
    public void updateComment(Comment comment) {
        Optional<Comment> byId = commentRepository.findById(comment.getId());
        if (byId.isPresent()) {
            Comment comment1 = byId.get();
            comment1.setParentid("2");
            commentRepository.save(comment1);
        }
    }
    /**
     * 根据id删除评论
     */
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }
    /**
     * 查询所有评论
     */
    public List<Comment> findCommentList(){
        return commentRepository.findAll();
    }
    /**
     * 根据id查询评论
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentRepository.findById(id).get();
    }
}
