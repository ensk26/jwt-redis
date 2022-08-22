package com.study.web.domain.member.service;

import com.study.web.domain.member.dao.MemberRepository;
import com.study.web.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    //MemberRepository를 접근하려면 무조건 service를 통해서, entity만 넘겨줘야해
    //web에서는 무조건 entity만 받아야해, domain안에 있는 내용은 무조건 entity만 넘겨주도록 만들어야함

    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
        //나중에 querydsl을 이용해 boolean(y/n 값으로도 바꾸는 작업 필요) 수정
    }
}
