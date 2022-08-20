package com.study.web;

import com.study.web.domain.member.dao.MemberRepository;
import com.study.web.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
public class MemberService {

   // private final MemberRepository memberRepository;

    //로그인 이메일 중복 확인, 데이터가 있어야 함 (다른곳)
//    public Member loginFindPassword(String email) {
//        return memberRepository.findByEmail(email);
//    }
}
