package com.hellcaster.blogging.service;

import com.hellcaster.blogging.config.EmailUtils;
import com.hellcaster.blogging.config.JwtService;
import com.hellcaster.blogging.entity.User;
import com.hellcaster.blogging.exception.AuthenticationFailedException;
import com.hellcaster.blogging.exception.RecordNotFoundException;
import com.hellcaster.blogging.exception.UserAlreadyRegisterException;
import com.hellcaster.blogging.model.JwtResponse;
import com.hellcaster.blogging.model.model_user.LoginUserRequest;
import com.hellcaster.blogging.model.model_user.RegisterUserRequest;
import com.hellcaster.blogging.repository.UserRespository;
import com.hellcaster.blogging.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    @Autowired
    private JwtService jwtService;

    @Override
    public User registerUser(RegisterUserRequest registerUserRequest){
        User u1 = userRespository.findByUserName(registerUserRequest.getUserName());
        if(!Objects.isNull(u1)){
            throw new UserAlreadyRegisterException("User Already Register", "USER_ALREADY_REGISTER");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .userName(registerUserRequest.getUserName())
                .role(registerUserRequest.getRole())
                .fullName(registerUserRequest.getFullName())
                .password(bCryptPasswordEncoder.encode(registerUserRequest.getPassword()))
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
    public User login(LoginUserRequest loginUserRequest)  throws  Exception {
        User u1 = userRespository.findByUserName(loginUserRequest.getUserName());
        if(Objects.isNull(u1)){
            throw new RecordNotFoundException("Email Id haven't Registered yet", "USER_NOT_FOUND");
        }
        User user = userRespository.findByUserNameAndPassword(loginUserRequest.getUserName(), loginUserRequest.getPassword());
        if(u1.getIsAcountVerify() == 0 && u1.getIsSocialRegister() == 0){
            throw new AuthenticationFailedException("Authentication Failed", "AUTHENTICATION_FAILED");
        }
        return u1;
    }

    @Override
    public User verifyEmailId(Integer otp, String userId) {
        User user = userRespository.findByUserIdAndOtp(userId, otp);
        if(Objects.isNull(user)){
            return null;
        }
        user.setIsAcountVerify(1);
        userRespository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        User user = userRespository.findByUserName(userName);
        if(user == null){
            log.error("UserService:loadUserByUsername Username not found: " + userName);
            throw new UsernameNotFoundException("Corresponding User Not Found");
        }
        log.info("UserService:loadUserByUsername User found: " + user);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().toArray(new String[0]))
                .build();
//        return new UserInfoDetails(user);
    }

    @Override
    public JwtResponse refreshToken(String token) {
        String userName = jwtService.extractUsername(token);
        UserDetails userDetails = loadUserByUsername(userName);
        if(userDetails.getUsername() == null){
            throw new RecordNotFoundException("User corresponding to this token Not Found", "USER_NOT_FOUND");
        }
        if(jwtService.validateToken(token, userDetails)){
            JwtResponse jwtResponse = new JwtResponse(jwtService.generateToken(userName), token);
            return jwtResponse;
        }
        return null;
    }
}