package com.hellcaster.blogging.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String  userId;
    private String fullName;
    private String userName;
    private String password;
    private List<String> role;
    private Integer isSocialRegister;
    private Integer otp;
    private Integer isAcountVerify;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
