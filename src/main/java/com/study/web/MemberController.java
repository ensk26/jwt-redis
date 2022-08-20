//package com.study.web;
//
//import com.study.web.domain.member.dao.MemberRepository;
//import com.study.web.domain.member.entity.Member;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//
//@Controller
//@RequiredArgsConstructor //private final 붙은 메서드 생성자 만들어 줌
//@RequestMapping("/members")
//@Slf4j
//public class MemberController {
//
//    //service를 통해서 repository를 가자, repository는 service를 통해서만 갈수 있는게 좋다.
//    private final MemberRepository memberRepository;
//    private final DuplicationValidate duplicationValidate;
//
//    @GetMapping("/add")
//    public String addForm(@ModelAttribute("member") Member member) {
//        return "members/addMemberForm";
//    }
//
//    @PostMapping("/add")
//    public String save(@Valid @ModelAttribute("member") Member member, BindingResult result) {
//        //회원가입 데이터가 올바른가 확인
//        if (result.hasErrors()) {
//            return "members/addMemberForm";
//        }
//
//        //중복된 이메일인지 확인
////        if (duplicationValidate.joinEmailDuplicate(member.getEmail())) {
////            log.info("중복가입된 이메일");
////            return "members/addMemberForm";
////        }
//
//        //데이터 형식이 올바르고, 중복되지 않은 이메일인 경우
//        log.info("가입 성공");
//        memberRepository.save(member);
//        return "redirect:/";
//    }
//}
