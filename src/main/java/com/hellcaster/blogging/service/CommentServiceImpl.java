package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Comment;
import com.hellcaster.blogging.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment createComment(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(String commentId) throws Exception {
        return commentRepository.deleteByCommentId(commentId);
    }

    @Override
    public List<Comment> getCommentsByBlogId(String blogId, Pageable pageable) throws Exception {
        return commentRepository.findByBlogId(blogId, pageable);
    }
}
