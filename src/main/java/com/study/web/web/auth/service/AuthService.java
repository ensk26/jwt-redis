package com.study.web.web.auth.service;

import com.study.web.DuplicationValidate;
import com.study.web.domain.jwtToken.entity.RefreshToken;
import com.study.web.domain.jwtToken.service.RefreshTokenService;
import com.study.web.domain.member.entity.Member;
import com.study.web.domain.member.service.MemberService;
import com.study.web.global.jwt.JwtHeaderUtilEnums;
import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.dto.JwtRequestDto;
import com.study.web.web.auth.dto.MemberLoginRequestDto;
import com.study.web.web.auth.dto.MemberSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.study.web.global.jwt.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
//@Transactional
//오류 발생시 롤백 해주는 어노테이션
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    //private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DuplicationValidate duplicationValidate;
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    public String signup(MemberSignupRequestDto requestDto) throws Exception {

        //이미 해당 이메일로 된 계정이 존재한다.
        if (memberService.findMember(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 해당 이메일로 된 계정이 존재");
        }

        Member member = requestDto.toEntity(passwordEncoder);
        memberService.saveMember(member);
        return member.getEmail();
    }



    public JwtRequestDto login(MemberLoginRequestDto requestDto) throws Exception {

        //스프링 시큐러티로 만든 provider가 반환한 Authentication 객체는 나중에 구현 해보자

        Member member = memberService.findMember(requestDto.getEmail())
                .orElseThrow(()->new NoSuchElementException("해당 이메일이 존재하지 않음"));
        //.orElseThrow 비어있는 파라미터를 던지면, NoSuchElementException가 발생함

        //비밀번호가 일치하는지
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀림");
            //적절하지 않은 인자나 메소드를 받을때 발생하는 오류
        }
        log.info("토큰 생성전");
        //uuid로 토큰 생성
        String email = member.getEmail();
        log.info(email);
        String accessToken = jwtTokenUtil.generateAccessToken(email);
        log.info(accessToken);
        String refreshToken = refreshTokenService
                .saveRefreshToken(email, jwtTokenUtil.generateAccessToken(email), REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        log.info(String.valueOf(refreshToken));
        log.info("토큰 생성후");
        return JwtRequestDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
