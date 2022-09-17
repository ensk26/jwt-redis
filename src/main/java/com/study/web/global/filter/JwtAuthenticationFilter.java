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
        String token = jwtTokenUtil.getToken(request);

        String email = jwtTokenUtil.getEmail(token);
        jwtTokenUtil.validateToken(token);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        processSecurity(request, userDetails);
        filterChain.doFilter(request,response);
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
