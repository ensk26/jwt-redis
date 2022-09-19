package com.study.web.infra.mail.constant;

import lombok.Getter;

@Getter
public enum MailMessage {


    JOIN_MAIL("회원가입 인증 이메일 입니다.",
            "홈페이지를 방문해주셔서 감사합니다."+"\n"+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요."+"\n",
            "seoduna17@gmail.com");


    private final String title; //이메일 제목

    private final String message; //이메일 내용

    private final String fromAddress; //보내는 이메일 주소

    MailMessage(String title, String message, String fromAddress) {
        this.title = title;
        this.message = message;
        this.fromAddress = fromAddress;
    }
}
