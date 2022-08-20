package com.study.web.web.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //인증, 인증: 정상적인 유저인지 판단, 인가: 정상적인 유저 중 어드민 권한인지, 일반사용자 권한인지 나타낼때 사용
        String email = authentication.getName();
        String password = (String)authentication.getCredentials();
        //뭘까

        CustomUser customUser = (CustomUser) userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, customUser.getMember().getPassword())) {
            throw new BadCredentialsException("비밀번호 불일치");
        }

        return new UsernamePasswordAuthenticationToken(customUser,null,customUser.getAuthorities());
        //인증이 완료후 SecurityContextHolder.getContext()에 등록될 Authentication 객체이다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

//provider로 판단해서 맞으면 accesstoken 생성해주는 식으로 