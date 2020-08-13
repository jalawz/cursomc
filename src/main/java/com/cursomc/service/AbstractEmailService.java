package com.cursomc.service;

import java.util.Date;

import com.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail (final Pedido pedido) {
        final SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(message);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido (final Pedido pedido) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(pedido.getCliente().getEmail());
        message.setFrom(sender);
        message.setSubject(String.format("Pedido confirmado! Código: %s", pedido.getId()));
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText(pedido.toString());

        return message;
    }
}
