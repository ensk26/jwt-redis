package com.study.web.web.auth.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    private String password;

    private String repassword;

}
