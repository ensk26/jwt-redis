package com.study.web.web.auth.controller;


import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.dto.JwtResponseDto;
import com.study.web.web.auth.dto.MemberLoginRequestDto;
import com.study.web.web.auth.dto.MemberSignupRequestDto;
import com.study.web.web.auth.service.AuthService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @ApiOperation(value = "회원로그인 API",
            notes = "사용자 email, 비밀번호를 이용해 로그인을 진행후 access token과 refresh token 반환해준다.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다.")
            , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
    })

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) throws Exception {
        return ResponseEntity.ok(authService.login(memberLoginRequestDto));
    }

    //email과 refreshtoken을 받는다.
    @ApiOperation(value = "토큰 재발급 API", notes= "refresh token을 이용해 새로운 access token을 반환해준다.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다.")
            , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
    })

    @PostMapping("/re-issue")
    public ResponseEntity<JwtResponseDto> reIssue(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        return ResponseEntity.ok(authService.reIssueAccessToken(refreshToken));
    }

    @ApiOperation(value = "로그아웃 API", notes= "access token을 이용해 로그아웃을 진행한다.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다.")
            , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
    })

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        authService.logout(refreshToken);
        //AuthService
    }

    //회원탈퇴, 닉네임 변경, 비밀번호 변경

    @ApiOperation(value = "회원탈퇴 API", notes= "access token을 이용해 회원탈퇴를 진행한다.")

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다.")
            , @ApiResponse(code = 400, message = "접근이 올바르지 않습니다.")
    })

    @PostMapping("/withdrawal")
    public void Withdrawal(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        authService.Withdrawal(refreshToken);
    }

    private String resolveToken(String accessToken) {
        return accessToken.substring(7);
    }

}

//restController랑, controller, 기본 생성자 기억하자, 예외처리.. response body 공부, controllerAdvice,