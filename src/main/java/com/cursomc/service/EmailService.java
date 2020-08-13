package com.cursomc.service;

import com.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail (Pedido pedido);

    void sendEmail (SimpleMailMessage msg);
}
