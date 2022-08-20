package com.study.web.web.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class JwtRequestDto {

    //로그인 요청 정보 전달 객체
    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtRequestDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    //DTo에 builder를 만들어, 아니면 밖에 만들어


}
