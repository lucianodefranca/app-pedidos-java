package com.luciano.apppedidos.services;

import org.springframework.mail.SimpleMailMessage;
import com.luciano.apppedidos.entities.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}
