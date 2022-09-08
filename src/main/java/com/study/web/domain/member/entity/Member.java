package com.study.web.domain.member.entity;

import com.study.web.global.model.Role;
import com.study.web.web.auth.dto.MemberSignupRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Getter
//기본 생성자
@NoArgsConstructor
@Entity
public class Member {

    //토큰에 저장할 id
    //private String uuid; //뺀다.
    @Id
    @NotNull
    @Column(length = 100)
    private String email;

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(length = 200)
    private String password;

    private Role role; //뺀다.

    //entity는 dto의 존재를 몰라야한다.,dto에 종속될수있으니 하지 말자..

    @Builder
    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        role=Role.USER;
    }
    //builder, new , 정적팩토리 메서드의 차이점

}
