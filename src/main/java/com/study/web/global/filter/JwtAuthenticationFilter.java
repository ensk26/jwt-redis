package com.study.web.global.filter;

import com.study.web.domain.jwtToken.dao.LogoutAccessTokenRedisRepository;
import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //private final LogoutAccessTokenRedisRepository logoutAccessTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        //if (token != null) {
        //checkLogout(accessToken);
        log.info(token);
        String email = jwtTokenUtil.getEmail(token);
        jwtTokenUtil.validateToken(token);
        //todo: email과 validateToken 순서에 대해 왜 signature 오류만 나는지에 대해
        //if (email != null) {
        //validateAccessToken(token, userDetails);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        processSecurity(request, userDetails);
                log.info("성공");
            //}
            
        //}
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");
        //헤더에서 "Bearer "만 제외하고 반환
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        //클라이언트에서 header로 token을 안보내주면 null 반환
        return null;
    }

    /*private void checkLogout(String accessToken) {
        if (logoutAccessTokenRepository.existsById(accessToken)) {
            throw new IllegalArgumentException("이미 로그아웃된 회원입니다.");
        }
    }*/

    //token유효성검사
    private void validateAccessToken(String token, UserDetails userDetails) {
        if (!jwtTokenUtil.validateToken(token, userDetails)) {
            throw new IllegalArgumentException("토큰 검증 실패");
        }
    }


    //securityContext에 해당 유저 정보를 넣어주는 과정인것 같다...
    private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
        //뭘 까??
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
