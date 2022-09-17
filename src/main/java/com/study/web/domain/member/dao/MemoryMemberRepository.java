package com.study.web.domain.member.dao;

import com.study.web.domain.member.dao.MemberRepository;
import com.study.web.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MemoryMemberRepository{//} implements MemberRepository {

    private Map<String, Member> store = new HashMap<>();

    //@Override
    public void save(Member member) {
        store.put(member.getEmail(),member);
    }

    //@Override
    public Member findByEmail(String email) {
        return store.get(email);
    }
}
