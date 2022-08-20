//package com.study.web.web.login.service;
//
//import com.study.web.domain.member.entity.Member;
//import com.study.web.MemberService;
//import com.study.web.web.login.dto.LoginDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class LoginService {
//
//    //private final MemberRepository memberRepository;
//    //private final DuplicationValidate duplicationValidate;
//    private final MemberService memberService;
//
//    public boolean login(LoginDto loginDto){
//        //가입된 계정이 있는가?
//        Member loginMember = memberService.loginFindPassword(loginDto.getEmail());
//
//        log.info("login? {}", loginMember);
//
//        //저장된 password와 입력된 password가 같은지 확인
//        if(loginMember!=null){
//            return loginMember.getPassword().equals(loginDto.getPassword());
//        }
//
//        //해당계정으로 로그인 한사람이 있는가
//        return false;
//    }
//
//}
