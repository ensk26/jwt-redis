package com.study.web.web.auth.controller;


import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.dto.JwtRequestDto;
import com.study.web.web.auth.dto.MemberLoginRequestDto;
import com.study.web.web.auth.dto.MemberSignupRequestDto;
import com.study.web.web.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    //회원가입, 로그인, 로그아웃 controller

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    //json 형식으로 데이터 받기 위해 @RequestBody 이용
    public String signup(@RequestBody MemberSignupRequestDto requestDto) throws Exception {
        return authService.signup(requestDto);
    }


    //ResponseEntity 응답클래스, 헤더정보와 http 상태코드를 함께 가공
    @PostMapping("/login")
    public ResponseEntity<JwtRequestDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) throws Exception {
        return ResponseEntity.ok(authService.login(memberLoginRequestDto));
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String accessToken,
                       @RequestHeader("RefreshToken") String refreshToken) {
        String uuid = jwtTokenUtil.getEmail(resolveToken(accessToken));
        //AuthService
    }

    private String resolveToken(String accessToken) {
        return accessToken.substring(7);
    }
}

//restController랑, controller, 기본 생성자 기억하자, 예외처리.. response body 공부, controllerAdvice,