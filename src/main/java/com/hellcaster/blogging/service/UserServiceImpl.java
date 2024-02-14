package com.hellcaster.blogging.service;

import com.hellcaster.blogging.config.EmailUtils;
import com.hellcaster.blogging.entity.User;
import com.hellcaster.blogging.exception.AuthenticationFailedException;
import com.hellcaster.blogging.exception.RecordNotFoundException;
import com.hellcaster.blogging.exception.UserAlreadyRegisterException;
import com.hellcaster.blogging.model.LoginUserRequest;
import com.hellcaster.blogging.model.RegisterUserRequest;
import com.hellcaster.blogging.repository.UserRespository;
import com.hellcaster.blogging.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private EmailUtils emailUtils;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.mail.verification-link}")
    private String mailVerificationLink;
    @Autowired
    private AppUtils appUtils;
    @Override
    public User registerUser(RegisterUserRequest registerUserRequest){
        User u1 = userRespository.findByUserName(registerUserRequest.getUserName());
        if(!Objects.isNull(u1)){
            throw new UserAlreadyRegisterException("User Already Register", "USER_ALREADY_REGISTER");
        }
        User user = User.builder()
                .userName(registerUserRequest.getUserName())
                .role(registerUserRequest.getRole())
                .fullName(registerUserRequest.getFullName())
                .password(registerUserRequest.getPassword())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isSocialRegister(0)
                .isAcountVerify(0)
                .otp(appUtils.get4DigitOtp())
                .build();
        User savedUser = userRespository.save(user);
        emailUtils.sendEmail(fromEmail, savedUser.getUserName(),
                "Email verification for our blogging website",
                "Click on below link to verify your email\n"
                        + mailVerificationLink + user.getOtp() + savedUser.getUserId());
        return user;
    }

    @Override
    public User login(LoginUserRequest loginUserRequest) {
        User u1 = userRespository.findByUserName(loginUserRequest.getUserName());
        if(Objects.isNull(u1)){
            throw new RecordNotFoundException("Email Id haven't Registered yet", "USER_NOT_FOUND");
        }
        User user = userRespository.findByUserNameAndPassword(loginUserRequest.getUserName(), loginUserRequest.getPassword());
        if(Objects.isNull(user)){
            throw new AuthenticationFailedException("Authentication Failed", "AUTHENTICATION_FAILED");
        }
        return user;
    }

    @Override
    public Boolean verifyEmailId(Integer otp, String userId) {
        User user = userRespository.findByUserIdAndOtp(userId, otp);
        return !Objects.isNull(user);
    }
}