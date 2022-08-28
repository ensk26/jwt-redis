package com.study.web.web.auth.service;

import com.study.web.domain.member.entity.Member;
import com.study.web.domain.member.service.MemberService;
import com.study.web.global.cache.CacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    //login 요청이 오면 자동으로 UserDetailService 타입으로 loc 되어 있는 loadUserByUsername 함수가 실행
    private final MemberService memberService;

    @Override
    @Cacheable(value = CacheKey.USER, key = "#email", unless = "#result==null")
    //토큰을 줄때마다 데이터베이스를 거치는것을 줄임
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findMember(email)
                .orElseThrow(()-> new NoSuchElementException("등록되지 않은 사용자"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        //나중에 수정 해야함, getRole 대체
        return new CustomUser(member,roles);
    }
}
