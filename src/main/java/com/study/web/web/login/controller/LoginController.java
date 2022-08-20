/*
package com.study.web.web.login.controller;

import com.study.web.web.login.dto.LoginDto;
import com.study.web.web.login.service.LoginService;
import com.study.web.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginDto loginDto){
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute("loginForm") LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request) {
        //@Vaild 옆에 @ModelAttribute 들어오는 객체 검증
        //@RequestParam, 1:1매핑, @ModelAttribute 객체 매핑
        if (bindingResult.hasErrors()) {
            log.info("데이터 형식 잘못됨");
            return "login/loginForm";
        }

        if(!loginService.login(loginDto)){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //session 존재 유무

        // String requestURI = request.getRequestURI();

        //log.info("인증 체크 인터셉터 실행 {}", requestURI);

        //세션 id만 쿠키로 전달 이걸 서버가 알아서 누구인지 체크, uuid, 세션 저장소




        //loginservic에서 처리하면 되지 않을까

        HttpSession session = request.getSession(); //세션이 존재하지 않으면 세션 생성

        if (session.getAttribute(SessionConst.LOGIN_MEMBER) == loginDto.getEmail()) {
            log.info("세션이 존재, 로그인 실패");

            return "login/loginForm";
        }
        log.info("email: {}",session);

        //로그인 성공 처리

        //session을 생성, 이메일 key로 회원정보 저장
        log.info("세션이 존재 안함, 로그인 성공");
        session.setAttribute(SessionConst.LOGIN_MEMBER ,loginDto);
        return "redirect:/";

    }
}
*/
