package com.study.web.web.auth.service;

import com.study.web.domain.member.entity.Member;
import com.study.web.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    //login 요청이 오면 자동으로 UserDetailService 타입으로 loc 되어 있는 loadUserByUsername 함수가 실행
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Member member = memberService.findMember(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException("등록되지 않은 사용자"));

        List<GrantedAuthority> roles = new ArrayList<>();
        //roles.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        //나중에 수정 해야함, getRole 대체
        return new CustomUser(member,roles);
    }
}
