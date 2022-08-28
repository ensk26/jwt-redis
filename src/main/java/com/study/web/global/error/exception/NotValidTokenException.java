package com.study.web.global.error.exception;

public class NotValidTokenException extends BusinessException{
    public NotValidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
