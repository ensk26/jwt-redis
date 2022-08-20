//package com.study.web;
//
//import com.study.web.web.auth.dto.MemberSignupRequestDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
//
//@Getter
//@Setter
////기본 생성자
//@NoArgsConstructor
//
//public class Member1 {
//
//    @Email
//    @NotEmpty
//    private String email;
//
//    @NotEmpty
//    private String name;
//
//    @NotEmpty
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
//    private String password;
//    //대문자,소문자,숫자,특수기호 포함 인증
//
//
//    void member(MemberSignupRequestDto requestDto) {
//        this.email
//    }
//}
