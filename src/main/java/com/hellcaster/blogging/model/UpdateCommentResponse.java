package com.hellcaster.blogging.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentResponse {
    @NotBlank(message = "Comment Id cannot be blank")
    private String commentId;
    @NotBlank(message = "User Id cannot be blank")
    private String userId;
    @NotBlank(message = "Blog Id cannot be blank")
    private String blogId;
    @NotBlank(message = "Comment cannot be blank")
    private String comment;
}
