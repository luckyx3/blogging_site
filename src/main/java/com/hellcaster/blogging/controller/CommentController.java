package com.hellcaster.blogging.controller;

import com.hellcaster.blogging.entity.Comment;
import com.hellcaster.blogging.model.CommonPaginationRequest;
import com.hellcaster.blogging.model.DBResponseEntity;
import com.hellcaster.blogging.model.model_comment.CreateCommentRequest;
import com.hellcaster.blogging.model.model_comment.UpdateCommentRequest;
import com.hellcaster.blogging.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/v1/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBResponseEntity> createCommentCall(@Valid @RequestBody CreateCommentRequest cmtReq){
        log.info("Create Comment request received: {}", cmtReq.toString());
        try {
            Comment comment = commentService.createComment(cmtReq);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(comment)
                                                                .message("Comment Created Successfully")
                                                                .build();
            log.info("Comment Created Successfully: {}", comment);
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e){
            log.debug("CommentController:createCommentCall something when wrong : {}", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/v1/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBResponseEntity> updateCommentCall(@Valid @RequestBody UpdateCommentRequest upReq){
        log.info("Update Comment request received: {}", upReq.toString());
        try {
            Comment comment = commentService.updateComment(upReq);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(comment)
                                                                .message("Comment Updated Successfully")
                                                                .build();
            log.info("Comment Updated Successfully: {}", comment);
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e){
            log.debug("CommentController:updateCommentCall something when wrong : {}", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/v1/delete/{commentId}")
    @PreAuthorize("hasAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<DBResponseEntity> deleteCommentCall(@PathVariable String commentId){
        log.info("Delete Comment request received: {}", commentId);
        try {
            Comment comment = commentService.deleteComment(commentId);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(comment)
                                                                .message("Comment Deleted Successfully")
                                                                .build();
            log.info("Comment Deleted Successfully: {}", comment);
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e){
            log.debug("CommentController:deleteCommentCall something when wrong : {}", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/v1/get/{blogId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<DBResponseEntity> getCommentsCall(@RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "2") Integer pageSize,
                                                            @PathVariable("blogId") String blogId,
                                                            @RequestParam(defaultValue = "id") String sortBy){
        log.info("Get Comments Corresponding to Blog Id request received: {}", blogId);
        try {
            CommonPaginationRequest commonPaginationRequest = CommonPaginationRequest.builder()
                                                                                    .pageNo(pageNo)
                                                                                    .pageSize(pageSize)
                                                                                    .value(blogId)
                                                                                    .sortBy(sortBy)
                                                                                    .build();
            List<Comment> comments = commentService.getCommentsByBlogId(commonPaginationRequest);
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                                                                .data(comments)
                                                                .message("Comment Found")
                                                                .build();
            log.info("Comment Found: {}", comments);
            return ResponseEntity.ok(dbResponseEntity);
        }catch (Exception e){
            log.debug("CommentController:getCommentsCall something when wrong : {}", e);
            return ResponseEntity.status(500).build();
        }
    }
}
