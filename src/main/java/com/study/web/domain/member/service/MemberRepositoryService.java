package com.study.web.domain.member.service;

import com.study.web.domain.member.dao.MemberRepository;
import com.study.web.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRepositoryService {
    //MemberRepository를 접근하려면 무조건 service를 통해서, entity만 넘겨줘야해
    //web에서는 무조건 entity만 받아야해, domain안에 있는 내용은 무조건 entity만 넘겨주도록 만들어야함

    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }

    public void updatePassword(Long id, String password) {
        memberRepository.updatePasswordById(id,password);
    }

    public void updateName(Long id, String name) {
        memberRepository.updateNameById(id,name);
    }


}
