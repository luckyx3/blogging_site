package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.User;
import com.hellcaster.blogging.model.LoginUserRequest;
import com.hellcaster.blogging.model.RegisterUserRequest;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

public interface UserService {
    User registerUser(RegisterUserRequest registerUserRequest);
    User login(LoginUserRequest loginUserRequest);
    Boolean verifyEmailId(Integer otp, String userId);
}
