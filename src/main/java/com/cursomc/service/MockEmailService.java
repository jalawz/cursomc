package com.cursomc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;

@Slf4j
public class MockEmailService extends AbstractEmailService {

    @Override
    public void sendEmail (final SimpleMailMessage msg) {
        log.info("Simulando envio de e-mail...");
        log.info(msg.toString());
        log.info("E-mail enviado");
    }
}
