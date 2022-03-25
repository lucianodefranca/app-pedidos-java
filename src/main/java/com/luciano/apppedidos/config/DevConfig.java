package com.luciano.apppedidos.config;

import com.luciano.apppedidos.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService service;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean intanciaBaseDeDados() throws ParseException {

        if ("create".equals(strategy)) {
            return false;
        }
        service.InstanciaBaseDeDadosTeste();
        return true;
    }
}
