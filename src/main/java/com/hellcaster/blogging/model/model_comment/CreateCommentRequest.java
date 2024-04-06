package com.hellcaster.blogging.model.model_comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentRequest {
    @NotBlank(message = "userId cannot be blank")
    private String userId;
    @NotBlank(message = "blogId cannot be blank")
    private String blogId;
    @NotBlank(message = "comment cannot be blank")
    private String comment;
}
