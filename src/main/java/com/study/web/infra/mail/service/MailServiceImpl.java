package com.study.web.infra.mail.service;

import com.study.web.infra.mail.vo.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;

    @Override
    public Mail createMail(String toAddress, String title, String message, String fromAddress) {

        return Mail.builder()
                .toAddress(toAddress)
                .title(title)
                .message(message)
                .fromAddress(fromAddress)
                .build();
    }

    @Override
    public void sendMail(Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getToAddress());
        mailMessage.setSubject(mail.getTitle());
        mailMessage.setText(mail.getMessage());
        mailMessage.setFrom(mail.getFromAddress());
        mailMessage.setReplyTo(mail.getFromAddress());

        mailSender.send(mailMessage);
    }
}

//todo: 메일을 보내는 동안 기다리니까 비동기 처리 해줘야함