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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private ObjectId userId;
    private String fullName;
    private String emailId;
    private String password;
    private byte role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
