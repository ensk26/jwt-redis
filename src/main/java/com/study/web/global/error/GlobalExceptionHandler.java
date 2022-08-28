package com.study.web.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        log.error("NoSuchElementException", e);
        List<String> errorMessages = List.of(e.getMessage());
        HttpStatus httpStatus = HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()); //e.getStatus
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, errorMessages);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        List<String> errorMessages = List.of(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, errorMessages);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

