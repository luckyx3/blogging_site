package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.Blog;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.CreateBlogRequest;
import com.hellcaster.blogging.model.UpdateBlogRequest;

import java.util.List;

public interface BlogService {
    Blog createBlog(CreateBlogRequest createBlogRequest) throws Exception;
    Blog updateBlog(UpdateBlogRequest updateBlogRequest) throws Exception;
    Blog deleteBlog(String blogId) throws Exception;
    Blog getBlogById(String blogId) throws Exception;
    List<Blog> getBlogsByUserId(CommonPaginationRequest commonPaginationRequest) throws Exception;


}
