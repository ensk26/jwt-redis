package com.study.web.domain.jwtToken.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor
@RedisHash("logoutAccessToken")
public class LogoutAccessToken {
    private String id;

    @TimeToLive
    private Long expiration;

    @Builder
    public LogoutAccessToken(String id, Long expiration) {
        this.id = id;
        this.expiration = expiration;
    }

    //오류 때문에 만든거라고 하니까 주의
}
