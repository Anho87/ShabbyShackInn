package com.example.shabbyshackinn.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailConfig {
    
    private String host;
    private int port;
    private String username;
    private String password;
    
    @Autowired
    IntegrationProperties integrationProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(integrationProperties.getMailConfig().getHost());
        mailSender.setPort(integrationProperties.getMailConfig().getPort());
        mailSender.setUsername(integrationProperties.getMailConfig().getUsername());
        mailSender.setPassword(integrationProperties.getMailConfig().getPassword());

        // Aktivera SMTP-autentisering och TLS-start
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}


