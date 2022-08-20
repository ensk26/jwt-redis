package com.study.web.domain.member.dao;

import com.study.web.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //void save(Member member);

    Optional<Member> findByEmail(String email);


}
