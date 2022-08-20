package com.study.web.domain.jwtToken.service;

import com.study.web.domain.jwtToken.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,String> redisTemplate;

    public void setValues(String key, String data){
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    //spring 2.0 이상은 RedisConnectionFactory, RedisTemplate을 자동으로 만들어준다
    //redis 저장소에서 key, value를 이용해 데이터 저장 및 탐색, 삭제 가능
}
