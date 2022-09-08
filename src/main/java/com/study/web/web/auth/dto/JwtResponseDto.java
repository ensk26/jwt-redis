package com.study.web.web.auth.dto;

import com.study.web.global.jwt.JwtHeaderUtilEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "토큰 발급 응답 DTO")
@Getter
public class JwtResponseDto {

    //로그인 요청 정보 전달 객체
    @Schema(description = "토큰 타입")
    private String grantType;

    @Schema(description = "접속을 위한 토큰")
    private String accessToken;

    @Schema(description = "accessToken을 재요청 받기 위한 토큰")
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
