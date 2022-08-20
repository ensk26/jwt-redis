package com.study.web.web.auth.dto;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private String email;
    private String password;
}
