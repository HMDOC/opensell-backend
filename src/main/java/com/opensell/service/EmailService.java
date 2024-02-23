package com.opensell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Quoc Dung
 */
@CrossOrigin("http://localhost/")
@Service
@RestController
public class EmailService {
        @Autowired
        private JavaMailSender javaMailSender;
    
        public boolean sendEmail(String email, String subject, String text) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom("OpenSell <opensell.inc@outlook.com>");
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
