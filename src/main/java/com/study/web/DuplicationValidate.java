package com.study.web;

import com.study.web.domain.member.dao.MemberRepository;
import com.study.web.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicationValidate {

   // private final MemberRepository memberRepository;

//    // 이메일 중복 확인, 중복 체크만, 다른 방식으로 구현
//    public boolean EmailDuplicateValidate(String email) {
//       // Member member = memberRepository.findByEmail(email);
//        //return member!=null;
//    }
}
