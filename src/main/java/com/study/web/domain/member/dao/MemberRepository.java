package com.study.web.domain.member.dao;

import com.study.web.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //void save(Member member);

    Member findByEmail(String email);

    //한가지 작업 이상을 한다면 transactional 필수
    @Transactional
    void deleteByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.name = :name where m.id = :id")
    void updateNameById(@Param(value="id")Long id, @Param(value="name")String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :password where m.id = :id")
    void updatePasswordById(@Param(value="id")Long id, @Param(value="password")String password);

}
