package com.hellcaster.blogging.controller;

import com.hellcaster.blogging.entity.Blog;
import com.hellcaster.blogging.exception.RecordNotFoundException;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.CreateBlogRequest;
import com.hellcaster.blogging.model.DBResponseEntity;
import com.hellcaster.blogging.model.UpdateBlogRequest;
import com.hellcaster.blogging.service.BlogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @PostMapping("/v1/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBResponseEntity> createBlogCall(@Valid @RequestBody CreateBlogRequest createBlogRequest){
        log.info("Create Blog request received: {}", createBlogRequest.toString());
        try {
            Blog createdBlog = blogService.createBlog(createBlogRequest);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                .data(createdBlog)
                                                .message("Blog Created Successfully")
                                                .build();
            log.info("Blog Created Successfully: {}", createdBlog);
            return ResponseEntity.ok(dbResponseEntity);
        } catch (Exception e) {
            log.debug("BlogController:createBlogCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/v1/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBResponseEntity> updateBlogCall(@Valid @RequestBody UpdateBlogRequest updateBlogRequest){
        log.info("Update Blog request received: {}", updateBlogRequest.toString());
        try {
            Blog updatedBlog = blogService.updateBlog(updateBlogRequest);
            if(ObjectUtils.isEmpty(updatedBlog)){
                throw new RecordNotFoundException("NOT_FOUND", "Blog with given ID not Found");
            }
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                .data(updatedBlog)
                                                .message("Blog Updated Successfully")
                                                .build();
            log.info("Blog Updated Successfully: {}", updatedBlog);
            return ResponseEntity.ok(dbResponseEntity);
        }catch (RecordNotFoundException e){
            log.debug("BlogController:updateBlogCall something when wrong : {}", e);
            throw e;
        }
        catch (Exception e) {
            log.debug("BlogController:updateBlogCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/v1/get/{blogId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<DBResponseEntity> getBlogCall(@PathVariable String blogId){
        log.info("Get Blog request received: {}", blogId);
        try {
            Blog blog = blogService.getBlogById(blogId);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(blog)
                                                                .message("Blog Found").build();
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e) {
            log.debug("BlogController:getBlogCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/v1/delete/{blogId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<DBResponseEntity> deleteBlogCall(@PathVariable String blogId){
        log.info("Delete Blog request received: {}", blogId);
        try {
            Blog blog = blogService.deleteBlog(blogId);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(blog)
                                                                .message("Blog Deleted").build();
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e){
            log.debug("BlogController:deleteBlogCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/v1/get_blogs/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<DBResponseEntity> getBlogsCall(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "1") String userId,
                                                         @RequestParam(defaultValue = "id") String sortBy){
        log.info("Get Blogs request received");
        try {
            CommonPaginationRequest commonPaginationRequest = CommonPaginationRequest.builder()
                                                                                    .pageNo(pageNo)
                                                                                    .pageSize(pageSize)
                                                                                    .sortBy(sortBy)
                                                                                    .value(userId)
                                                                                    .build();
            List<Blog> blogs = blogService.getBlogsByUserId(commonPaginationRequest);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(blogs)
                                                                .message("Blog Found").build();
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e) {
            log.debug("BlogController:getBlogCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
