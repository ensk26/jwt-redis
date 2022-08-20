package com.study.web.web.auth.dto;

import com.study.web.domain.member.entity.Member;
import com.study.web.global.model.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;


@Getter
//불변하게 사용하기 위해, setter 대신 생성자 생성
public class MemberSignupRequestDto {

    //회원가입 요청 데이터 전달 객체
    private String email;
    private String name;
    private String password;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
    }

    //dto에서 vailidation 하기
}
