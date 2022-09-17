package com.study.web.domain.member.dao;

import com.study.web.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //void save(Member member);

    Member findByEmail(String email);

    //한가지 작업 이상을 한다면 transactional 필수
    @Transactional
    void deleteByEmail(String email);
}
