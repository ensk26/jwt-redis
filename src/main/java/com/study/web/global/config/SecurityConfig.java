package com.study.web.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.web.domain.jwtToken.dao.LogoutAccessTokenRedisRepository;
import com.study.web.global.filter.ExceptionHandlerFilter;
import com.study.web.global.filter.JwtAuthenticationFilter;
import com.study.web.global.jwt.JwtEntryPoint;
import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtEntryPoint jwtEntryPoint;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;

    private final ObjectMapper objectMapper;

    //정적 파일에 대한 요청들
    private static final String[] AUTH_WHITELLIST = {
            "/auth/", "/auth/join", "/auth/login", "/auth/reissue",
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**"
    };


    //configure
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests()
                //login 없이 접근 허용하는 url
                .antMatchers(AUTH_WHITELLIST).permitAll()
                // ADMIN권한이 있는 사용자만 접근 가능 url
                //.antMatchers("/admin/**").hasRole("ADMIN")
                //그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                //.authenticationEntryPoint 401 에러
                //JwtEntryPoint 시큐리티 필터 과정중 에러가 발생할경우 JwtEntryPoint에서 처리하도록

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                //jwt로 로그인/로그아웃을 처리할것이라서, logoutdisable

                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenUtil, customUserDetailService),
                        UsernamePasswordAuthenticationFilter.class)
                //jwtAuthenticationFilter를  UsernamePasswordAuthenticationFilter 전에 추가 하겠다는 의미
                .addFilterBefore(new ExceptionHandlerFilter(objectMapper), JwtAuthenticationFilter.class)
                .csrf().disable()
                .httpBasic();
        return httpSecurity.build();
    }
    //성공 핸들러, 실패 핸들러, 로그인 창 커스텀, username 지정

    //정적 파일에 대한 요청 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELLIST);
    }

    //회원가입 시 비밀번호 암호화에 사용될 Encoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //2개부족
}
