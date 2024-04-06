package com.hellcaster.blogging.repository;

import com.hellcaster.blogging.entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
    Blog deleteByBlogId(String blogId);
    Blog findByBlogId(String blogId);
    List<Blog> findBlogsByUserId(String userId, Pageable pageable);
}
