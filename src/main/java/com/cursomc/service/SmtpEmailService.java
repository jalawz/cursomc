package com.cursomc.service;

import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail (final SimpleMailMessage msg) {
        log.info("Simulando envio de e-mail...");
        mailSender.send(msg);
        log.info("E-mail enviado");
    }

    @Override
    public void sendHTMLTEmail (final MimeMessage message) {
        log.info("Simulando envio de e-mail...");
        javaMailSender.send(message);
        log.info("E-mail enviado");
    }
}
