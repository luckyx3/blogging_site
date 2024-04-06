package com.hellcaster.blogging.model.model_comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentRequest {
    @NotBlank(message = "Comment Id cannot be blank")
    private String commentId;
    @NotBlank(message = "User Id cannot be blank")
    private String userId;
    @NotBlank(message = "Blog Id cannot be blank")
    private String blogId;
    @NotBlank(message = "Comment cannot be blank")
    private String comment;
}
