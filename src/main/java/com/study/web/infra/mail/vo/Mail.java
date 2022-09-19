package com.study.web.infra.mail.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Mail {
    private String toAddress; //받는 이메일 주소

    private String title; //이메일 제목

    private String message; //이메일 내용

    private String fromAddress; //보내는 이메일 주소

    @Builder
    public Mail(String toAddress, String title, String message, String fromAddress) {
        this.toAddress = toAddress;
        this.title = title;
        this.message = message;
        this.fromAddress = fromAddress;
    }
}
