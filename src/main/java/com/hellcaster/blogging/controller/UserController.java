package com.hellcaster.blogging.controller;

import com.hellcaster.blogging.config.JwtService;
import com.hellcaster.blogging.entity.User;
import com.hellcaster.blogging.exception.AuthenticationFailedException;
import com.hellcaster.blogging.exception.RecordNotFoundException;
import com.hellcaster.blogging.exception.UserAlreadyRegisterException;
import com.hellcaster.blogging.model.DBResponseEntity;
import com.hellcaster.blogging.model.JwtResponse;
import com.hellcaster.blogging.model.LoginUserRequest;
import com.hellcaster.blogging.model.RegisterUserRequest;
import com.hellcaster.blogging.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("v1/register")
    public ResponseEntity<DBResponseEntity> registerCall(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        log.info("UserController:registerCall Register User request received: {}", registerUserRequest);
        try{
            User user = userService.registerUser(registerUserRequest);
//            JwtResponse jwtResponse = new JwtResponse("Verified Token");
            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                    .message("User Registered Successfully")
                    .build();
            log.info("User Registered Successfully: {}", user);
            return ResponseEntity.ok(dbResponseEntity);
        } catch (UserAlreadyRegisterException e) {
            log.debug("UserController:registerCall User already present in the System : {}", e);
            throw e;
        } catch (Exception e) {
            log.debug("UserController:registerCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("v1/login")
    public ResponseEntity<DBResponseEntity> loginCall(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        log.info("UserController:loginCall Login User request received: {}", loginUserRequest);
        try {
            User user = userService.login(loginUserRequest);
            //Authentication while Login
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getUserName(), loginUserRequest.getPassword()));
            if(!authentication.isAuthenticated()){
                throw new AuthenticationFailedException("Password or Email Id is incorrect", "AUTHENTICATION_FAILED");
            }
            //Generate JWT Token
            JwtResponse jwtResponse = new JwtResponse(jwtService.generateToken(user.getUserName()));

            DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                    .data(jwtResponse)
                    .message("User Logged In Successfully")
                    .build();

            log.info("User Logged In Successfully: {}", user);

            return ResponseEntity.ok(dbResponseEntity);
        }
        catch (RecordNotFoundException ex){
            log.debug("UserController:loginCall User not present in the System : {}", ex);
            throw ex;
        }
        catch (AuthenticationFailedException ex){
            log.debug("UserController:loginCall Authentication Failed : {}", ex);
            throw ex;
        }
        catch (Exception e) {
            log.debug("UserController:loginCall something when wrong : {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("v1/verifyEmailId/{code}")
    public ResponseEntity<DBResponseEntity<JwtResponse>> verifyEmailIdCall(@PathVariable String code) {
        log.info("UserController:verifyEmailIdCall request received: {}", code);
        try {
            System.out.println(Integer.parseInt(code.substring(0, 4)) + " : " + code.substring(4));
            User user =  userService.verifyEmailId(Integer.parseInt(code.substring(0,4)), code.substring(4));
            if(!Objects.isNull(user)){
                JwtResponse jwtResponse = new JwtResponse(jwtService.generateToken(user.getUserName()));
                DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                        .data(jwtResponse)
                        .message("User Verified and Login Successfully")
                        .build();
                return ResponseEntity.ok(dbResponseEntity);
            }
            else{
                throw new RecordNotFoundException("User Not Present int the System", "USER_NOT_FOUND");
            }
        }
        catch (RecordNotFoundException ex){
            log.debug("UserController:verifyEmailIdCall User not present in the System : {}", ex);
            throw ex;
        }catch (AuthenticationFailedException exception) {
            log.debug("UserController:verifyEmailIdCall Authentication failed exception : {}", exception);
            throw exception;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
