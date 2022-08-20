//package com.study.web.web.login.dto;
//
//import lombok.Getter;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
//
//@Getter
//public class LoginDto {
//
//    //로그인 데이터 전달하기 위한 객체
//    @Email
//    @NotEmpty
//    private String email;
//
//    @NotEmpty
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
//    private String password;
//
//    //불변하게 사용하기 위해, setter 대신 생성자 생성
//    public LoginDto(@Email @NotEmpty String email, @NotEmpty String password) {
//        this.email = email;
//        this.password = password;
//    }
//}
