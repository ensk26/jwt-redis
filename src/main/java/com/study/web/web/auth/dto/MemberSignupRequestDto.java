package com.study.web.web.auth.dto;

import com.study.web.domain.member.entity.Member;
import com.study.web.global.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Schema(description = "회원가입 요청 DTO")
@Getter
//불변하게 사용하기 위해, setter 대신 생성자 생성
public class MemberSignupRequestDto {

    //회원가입 요청 데이터 전달 객체

    @Schema(description = "이메일", minLength = 1, maxLength = 100)
    @Email
    @NotEmpty
    @Size(min=1, max=100)
    private String email;

    @Schema(description = "이름", minLength = 1, maxLength = 100)
    @NotEmpty
    @Size(min=1, max=100)
    private String name;

    @Schema(description = "비밀번호", minLength = 1, maxLength = 200)
    @NotEmpty
    @Size(min=1, max=200)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
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
