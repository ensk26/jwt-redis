package com.study.web.domain.jwtToken.service;

//import com.study.web.domain.jwtToken.dao.RefreshTokenRedisRepository;
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

    public String saveRefreshToken(String id, String refreshToken, Long expiration) {
        log.info(id);
        RefreshToken token = RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .expiration(expiration/1000)
                .build();
        log.info(token.getId());
        //log.debug(String.valueOf(refreshTokenRepository.save(token)));
       // return refreshTokenRepository.save(token);
        redisService.setValues(id,refreshToken, Duration.ofMillis(expiration));
        return redisService.getValues(id);
    }

    public String findRefreshToken(String id){
        return redisService.getValues(id);
    }
}
