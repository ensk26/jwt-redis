package com.study.web.domain.jwtToken.dao;

import com.study.web.domain.jwtToken.entity.RefreshToken;
import com.study.web.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryRefreshTokenRedisRepository implements RefreshTokenRedisRepository{
    private Map<String,RefreshToken> store = new HashMap<>();
    @Override
    public RefreshToken save(RefreshToken token) {
        store.put(token.getId(), token);
        return store.get(token.getId());
    }
}
