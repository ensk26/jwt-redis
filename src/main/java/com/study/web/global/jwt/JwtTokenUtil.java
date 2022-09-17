package com.study.web.global.jwt;

import com.study.web.global.error.exception.ErrorCode;
import com.study.web.global.error.exception.NotValidTokenException;
import com.study.web.global.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

    public String generateAccessToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())                // 토큰 제목
                .setAudience(email)                                 // 토큰 대상자
                .setIssuedAt(new Date())                            // 토큰 발급 시간
                .setExpiration(createAccessTokenExpireTime())       // 토큰 만료 시간
                .claim("role", role)                          // 유저 role
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    }

    public String generateRefreshToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())                // 토큰 제목
                .setAudience(email)                                 // 토큰 대상자
                .setIssuedAt(new Date())                            // 토큰 발급 시간
                .setExpiration(creatRefreshTokenExpireTime())       // 토큰 만료 시간
                .claim("role", role)                          // 유저 role
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private Date creatRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getAudience();
        } catch (Exception e) {
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public String getToken(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");
        //헤더에서 "Bearer "만 제외하고 반환
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        //클라이언트에서 header로 token을 안보내주면 null 반환
        return null;
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

    /*public long getExpirationTime(String token) {
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
    }*/

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .requireSubject(TokenType.ACCESS.name())
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token);
        } catch (MissingClaimException |IncorrectClaimException ex) {
            // sub 필드가 없을때, sub 필드가 ACCESS.name()이 아닐때
            log.error("subject 잘못된 jwt token");
            throw new NotValidTokenException(ErrorCode.NOT_ACCESS_TOKEN);
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

    public void validateRefreshTokn(String token) {
        try {
            Jwts.parserBuilder()
                    .requireSubject(TokenType.REFRESH.name())
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token);
        } catch (MissingClaimException |IncorrectClaimException ex) {
            // sub 필드가 없을때, sub 필드가 REFRESH.name()이 아닐때
            log.error("subject 잘못된 jwt token");
            throw new NotValidTokenException(ErrorCode.NOT_REFRESH_TOKEN);
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

//todo: refresh token인지 값 확인, 만약 refresh token이면 해당 refresh token이 redis에 저장이 되있는지 확인(filter에서 해결 될것 같다)