package com.hellcaster.blogging.model.model_blog;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {
    private String blogId;
    private String userId;
    private String title;
    private String description;
    private Boolean publish;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
