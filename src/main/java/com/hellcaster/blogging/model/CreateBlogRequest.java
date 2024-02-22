package com.hellcaster.blogging.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateBlogRequest {
    @NotBlank(message = "userId is mandatory.")
    private String userId;
    @NotBlank(message = "Title is mandatory.")
    private String title;
    @NotBlank(message = "Description can't be blank.")
    private String description;
    private Boolean publish;
}
