package com.opensell.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Mail service to send customizable emails
 * @author Quoc Dung
 */
@Service
@RequiredArgsConstructor
public class EmailService {
        @Value("${SMTP_EMAIL}")
        private String senderEmail;

        private final JavaMailSender javaMailSender;
    
        public boolean sendEmail(String email, String subject, String text) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom("Opensell <" + senderEmail + ">");
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
