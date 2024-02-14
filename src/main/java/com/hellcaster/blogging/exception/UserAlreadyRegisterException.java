package com.hellcaster.blogging.exception;

import lombok.Data;

@Data
public class UserAlreadyRegisterException extends RuntimeException{
    public String errorCode;
    public UserAlreadyRegisterException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
