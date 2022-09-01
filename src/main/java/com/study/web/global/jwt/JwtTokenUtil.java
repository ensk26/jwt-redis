package com.study.web.global.jwt;

import com.study.web.global.error.exception.ErrorCode;
import com.study.web.global.error.exception.NotValidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static com.study.web.global.jwt.JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.study.web.global.jwt.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(String email) {
        return doGenerateToken(email,ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    }

    public String generateRefreshToken(String email) {
        return doGenerateToken(email, REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    }

    private String doGenerateToken(String email, Long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setSubject("ACCESS")
                .setClaims(claims)
                .setAudience(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .compact();
        //무슨 내용인지 공부, {토큰 대상자에 uuid 넣자}, access token인지, refresh token인지 판다
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmail(String token) {
        return extractAllClaims(token).get("email",String.class);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("exception msg",e);
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public long getExpirationTime(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiration.getTime()-now.getTime();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = getEmail(token);
        if (!isTokenExpired(token)) {
            throw new NotValidTokenException(ErrorCode.TOKEN_EXPIRED);
        }

        return email.equals(userDetails.getUsername());
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJwt(token);
        } catch (MalformedJwtException | SecurityException e) {
            log.error("잘못된 jwt token");
            throw new NotValidTokenException(ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("만료된 jwt token");
            throw new NotValidTokenException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 jwt token");
            throw new NotValidTokenException(ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 jwt token");
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
    }
}

//todo: customUser 수정, filter 수정, filter적용해서 전반적으로 수정