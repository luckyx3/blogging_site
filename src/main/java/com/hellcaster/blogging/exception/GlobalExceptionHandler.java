package com.hellcaster.blogging.exception;

import com.hellcaster.blogging.model.DBResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DBResponseEntity> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException){
        String errorMessage = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();
        HttpStatusCode errorCode = methodArgumentNotValidException.getStatusCode();
        DBResponseEntity dbResponseEntity = DBResponseEntity.builder().data(errorCode.toString()).message(errorMessage).build();
        return new ResponseEntity<>(dbResponseEntity, errorCode);
    }
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<DBResponseEntity> handleRecordNotFoundException(RecordNotFoundException recordNotFoundException){
        DBResponseEntity dbResponseEntity = DBResponseEntity.builder()
                .message(recordNotFoundException.getMessage())
                .data(recordNotFoundException.getErrorCode())
                .build();
        return new ResponseEntity<>(dbResponseEntity, HttpStatus.NOT_FOUND);
    }
}
