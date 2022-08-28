package com.study.web.web.auth.dto;

import com.study.web.global.jwt.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponseDto {

    //로그인 요청 정보 전달 객체
    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtResponseDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    //DTo에 builder를 만들어, 아니면 밖에 만들어
    public static JwtResponseDto toEntity(String accessToken, String refreshToken) {
        return JwtResponseDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
