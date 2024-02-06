package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Blog;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.CreateBlogRequest;
import com.hellcaster.blogging.model.UpdateBlogRequest;
import com.hellcaster.blogging.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
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
    public Blog updateBlog(UpdateBlogRequest updateBlogRequest) throws Exception {
        Blog blog = Blog.builder()
                        .title(updateBlogRequest.getTitle())
                        .description(updateBlogRequest.getDescription())
                        .blogId(updateBlogRequest.getBlogId())
                        .userId(updateBlogRequest.getUserId())
                        .publish(updateBlogRequest.getPublish())
                        .build();
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    @Override
    public Blog deleteBlog(String blogId) throws Exception {
        return blogRepository.deleteByBlogId(blogId);
    }

    @Override
    public Blog getBlogById(String blogId) throws Exception{
        return blogRepository.findByBlogId(blogId);
    }

    @Override
    public List<Blog> getBlogsByUserId(CommonPaginationRequest commonPaginationRequest) throws Exception{

        Pageable pageable = (Pageable) PageRequest.of(commonPaginationRequest.getPageNo(), commonPaginationRequest.getPageSize(),
                Sort.by(commonPaginationRequest.getSortBy()).descending());
        return blogRepository.findByUserId(commonPaginationRequest.getValue(), pageable);
    }
}
