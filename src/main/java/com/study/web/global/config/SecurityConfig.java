package com.study.web.global.config;

import com.study.web.global.jwt.JwtEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtEntryPoint jwtEntryPoint;
    //정적 파일에 대한 요청들
    private static final String[] AUTH_WHITELLIST = {

    };


    //configure
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authz) -> authz
                        //login 없이 접근 허용하는 url
                        .antMatchers("/auth/**").permitAll()
                        // ADMIN권한이 있는 사용자만 접근 가능 url
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        //그 외 모든 요청은 인증과정 필요
                        .anyRequest().authenticated()

                )
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                //.authenticationEntryPoint 401 에러
                //JwtEntryPoint 시큐리티 필터 과정중 에러가 발생할경우 JwtEntryPoint에서 처리하도록

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                //jwt로 로그인/로그아웃을 처리할것이라서, logoutdisable

                .and()
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
}
