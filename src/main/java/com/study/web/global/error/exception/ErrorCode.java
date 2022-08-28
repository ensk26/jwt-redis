package com.study.web.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    TOKEN_EXPIRED(401, "해당 토큰은 만료됐습니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료됐습니다."),
    NOT_VALID_TOKEN(401,"해당 토큰은 유효하지 않습니다.");

    private int status;
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
