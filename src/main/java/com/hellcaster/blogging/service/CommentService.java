package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Comment;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.model_comment.CreateCommentRequest;
import com.hellcaster.blogging.model.model_comment.UpdateCommentRequest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentService {
    Comment createComment(CreateCommentRequest cmtReq) throws Exception;
    Comment updateComment(UpdateCommentRequest upReq) throws Exception;
    Comment deleteComment(String commentId) throws  Exception;

    List<Comment> getCommentsByBlogId(CommonPaginationRequest pagination) throws Exception;
}
