package com.luciano.apppedidos.config;

import com.luciano.apppedidos.services.DBService;
import com.luciano.apppedidos.services.EmailService;
import com.luciano.apppedidos.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService service;

    @Bean
    public boolean intanciaBaseDeDados() throws ParseException {
        service.InstanciaBaseDeDadosTeste();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
