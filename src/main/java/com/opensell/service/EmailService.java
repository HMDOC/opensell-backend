package com.opensell.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.opensell.config.AppConfig;
import lombok.RequiredArgsConstructor;

/**
 * Mail service to send customizable emails
 * 
 * @author Quoc Dung
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private final AppConfig appConfig;
    private final JavaMailSender javaMailSender;

    public boolean sendEmail(String email, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("Opensell <" + appConfig.supportEmail() + ">");
            msg.setTo(email);
            msg.setSubject(subject);
            msg.setText(text);
            javaMailSender.send(msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
