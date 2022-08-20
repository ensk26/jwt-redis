package com.study.web.domain.jwtToken.dao;

import com.study.web.domain.jwtToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface RefreshTokenRedisRepository {//} extends CrudRepository<RefreshToken, String> {

    //jpa로 구현시 삭제
    RefreshToken save(RefreshToken token);
}
