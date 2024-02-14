package com.hellcaster.blogging.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserRequest {
    @NotBlank(message = "username cannot be blank")
    private String userName;
    @NotBlank(message = "password cannot be blank")
    private String password;
    private int isSocialRegister;

    @Override
    public String toString() {
        return "LoginUserRequest{" +
                "username='" + userName + '\'' +
                ", isSocialRegister=" + isSocialRegister +
                '}';
    }
}
