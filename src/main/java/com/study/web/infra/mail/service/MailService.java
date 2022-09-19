package com.study.web.infra.mail.service;

import com.study.web.infra.mail.vo.Mail;

public interface MailService {

    public Mail createMail(String toAddress, String title, String message);

    public void sendMail(Mail mail);
}
