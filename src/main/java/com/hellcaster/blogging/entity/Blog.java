package com.hellcaster.blogging.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Blog {
    @Id
    private String blogId;
    private String userId;
    private String title;
    private String description;
    private Boolean publish;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
