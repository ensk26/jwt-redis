package com.study.web.web.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Schema(description = "로그인 요청 DTO")
@Getter
public class MemberLoginRequestDto {


    @Schema(description = "이메일", minLength = 1, maxLength = 100)
    @Email
    @NotEmpty
    @Size(min=1, max=100)
    private String email;

    @Schema(description = "비밀번호", minLength = 1, maxLength = 200)
    @NotEmpty
    @Size(min=1, max=200)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
}
