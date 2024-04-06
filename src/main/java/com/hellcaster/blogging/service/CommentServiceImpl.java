package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Comment;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.model_comment.CreateCommentRequest;
import com.hellcaster.blogging.model.model_comment.UpdateCommentRequest;
import com.hellcaster.blogging.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment createComment(CreateCommentRequest cmtReq) throws Exception {
        Comment cmt = Comment.builder()
                .userId(cmtReq.getUserId())
                .blogId(cmtReq.getBlogId())
                .comment(cmtReq.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return commentRepository.save(cmt);
    }

    @Override
    public Comment updateComment(UpdateCommentRequest upReq) throws Exception {
        Comment comment = commentRepository.findByCommentId(upReq.getCommentId());
        comment.setComment(upReq.getComment());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(String commentId) throws Exception {
        return commentRepository.deleteByCommentId(commentId);
    }

    @Override
    public List<Comment> getCommentsByBlogId(CommonPaginationRequest paginationRequest) throws Exception {
        Pageable pageable = PageRequest.of(paginationRequest.getPageNo(), paginationRequest.getPageSize(),
                Sort.by(paginationRequest.getSortBy()).descending());
        List<Comment>  comments = commentRepository.findCommentsByBlogId(paginationRequest.getValue(), pageable);
        return comments;
    }
}
