package com.cursomc.service;

import javax.mail.internet.MimeMessage;

import com.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail (Pedido pedido);

    void sendEmail (SimpleMailMessage msg);

    void sendOrderConfirmationHTMLEmail (Pedido pedido);

    void sendHTMLTEmail (MimeMessage message);
}
