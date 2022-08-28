
package com.study.web.domain.jwtToken.dao;

import com.study.web.domain.jwtToken.entity.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken,String> {
}

