package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Blog;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.model_blog.CreateBlogRequest;
import com.hellcaster.blogging.model.model_blog.UpdateBlogRequest;
import com.hellcaster.blogging.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@CacheConfig(cacheNames = "Blog")
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Blog createBlog(CreateBlogRequest createBlogRequest) throws Exception{
        Blog blog = Blog.builder()
                        .title(createBlogRequest.getTitle())
                        .userId(createBlogRequest.getUserId())
                        .description(createBlogRequest.getDescription())
                        .publish(createBlogRequest.getPublish())
                        .build();
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    @Override
    @CachePut(key = "#updateBlogRequest.blogId")
    public Blog updateBlog(UpdateBlogRequest updateBlogRequest) throws Exception {
        Blog blog = blogRepository.findByBlogId(updateBlogRequest.getBlogId());
        if(ObjectUtils.isEmpty(blog)){
            return null;
        }
        BeanUtils.copyProperties(updateBlogRequest, blog);
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    @Override
    @CacheEvict(key = "#blogId")
    public Blog deleteBlog(String blogId) throws Exception {
        return blogRepository.deleteByBlogId(blogId);
    }

    @Override
    @Cacheable(key = "#blogId")
    public Blog getBlogById(String blogId) throws Exception{
        return blogRepository.findByBlogId(blogId);
    }

    @Override
    public List<Blog> getBlogsByUserId(CommonPaginationRequest commonPaginationRequest) throws Exception{
        Pageable pageable = PageRequest.of(commonPaginationRequest.getPageNo(), commonPaginationRequest.getPageSize(),
                Sort.by(commonPaginationRequest.getSortBy()).descending());
        List<Blog> blogs = blogRepository.findBlogsByUserId(commonPaginationRequest.getValue(), pageable);
        return blogs;
    }
}
