package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Comment;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment) throws Exception;
    Comment updateComment(Comment comment) throws Exception;
    Comment deleteComment(String commentId) throws  Exception;

    List<Comment> getCommentsByBlogId(String blogId, Pageable pageable) throws Exception;
}
