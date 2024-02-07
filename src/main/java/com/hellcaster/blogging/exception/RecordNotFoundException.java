package com.hellcaster.blogging.exception;

import lombok.Data;

@Data
public class RecordNotFoundException extends RuntimeException{
    public String errorCode;

    public RecordNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
