package com.hellcaster.blogging.entity;

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
public class Comment {
    @Id
    private ObjectId commentId;
    private ObjectId userId;
    private ObjectId blogId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
