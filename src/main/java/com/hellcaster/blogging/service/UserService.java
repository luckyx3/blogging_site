package com.hellcaster.blogging.service;

import com.hellcaster.blogging.entity.User;
import com.hellcaster.blogging.model.JwtResponse;
import com.hellcaster.blogging.model.model_user.LoginUserRequest;
import com.hellcaster.blogging.model.model_user.RegisterUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User registerUser(RegisterUserRequest registerUserRequest);
    User login(LoginUserRequest loginUserRequest) throws  Exception;
    User verifyEmailId(Integer otp, String userId);
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
    JwtResponse refreshToken(String token);
}
