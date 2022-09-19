package com.study.web.domain.jwtToken.service;

import com.study.web.domain.jwtToken.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    //private final RefreshTokenRedisRepository refreshTokenRepository;
    private final RedisService redisService;

    public void saveRefreshToken(String email, String refreshToken, Long expiration) {
        log.info(email);
        RefreshToken token = RefreshToken.builder()
                .id(email)
                .refreshToken(refreshToken)
                .expiration(expiration/1000)
                .build();
        log.info(token.getId());
        //log.debug(String.valueOf(refreshTokenRepository.save(token)));
       // return refreshTokenRepository.save(token);
        redisService.setValues(email,refreshToken, Duration.ofMillis(expiration));
        //return redisService.getValues(email);
    }

    public String findRefreshToken(String email){
        return redisService.getValues(email);
    }

    public void deleteRefreshToken(String email) {
        redisService.deleteValues(email);
    }
}
