package com.study.web.infra.mail.constant;

import lombok.Getter;

@Getter
public enum MailMessage {


    JOIN_MAIL("회원가입 인증 이메일 입니다.",
            "홈페이지를 방문해주셔서 감사합니다."+"\n"+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요."+"\n"),

    FIND_PASSWORD_MAIL("임시 비밀번호 이메일 입니다.",
                      "임시 비밀번호를 발급해드립니다."+"\n"+ "로그인 후 비밀번호 변경을 진행하시길 바랍니다."+"\n");

    private final String title; //이메일 제목

    private final String message; //이메일 내용


    MailMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
