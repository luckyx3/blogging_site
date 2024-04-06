package com.hellcaster.blogging.model.model_comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    @Id
    private String commentId;
    private String userId;
    private String blogId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
