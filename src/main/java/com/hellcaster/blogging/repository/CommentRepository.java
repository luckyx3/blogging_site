package com.hellcaster.blogging.repository;

import com.hellcaster.blogging.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment deleteByCommentId(String commentId);
    List<Comment> findCommentsByBlogId(String blogId, Pageable pageable);
    Comment findByCommentId(String commentId);
}
