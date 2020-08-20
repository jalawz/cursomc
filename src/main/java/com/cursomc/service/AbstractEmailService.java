package com.cursomc.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail (final Pedido pedido) {
        final SimpleMailMessage message = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(message);
    }

    @Override
    public void sendOrderConfirmationHTMLEmail (final Pedido pedido) {
        try {
            final MimeMessage message = prepareMimeMessageFromPedido(pedido);
            sendHTMLTEmail(message);
        } catch (final MessagingException e) {
            sendOrderConfirmationEmail(pedido);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido (final Pedido pedido) throws MessagingException {
        final MimeMessage message = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(pedido.getCliente().getEmail());
        helper.setFrom(sender);
        helper.setSubject(String.format("Pedido confirmado! Código: %s", pedido.getCliente().getEmail()));
        helper.setSentDate(new Date(System.currentTimeMillis()));
        helper.setText(htmlFromTemplatePedido(pedido), true);
        return message;
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

    protected String htmlFromTemplatePedido (final Pedido pedido) {
        final Context context = new Context();
        context.setVariable("pedido", pedido);
        return templateEngine.process("email/confirmacaoPedido", context);
    }
}
