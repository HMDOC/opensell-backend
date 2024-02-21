package com.opensell.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
                msg.setFrom("OpenSell <opensell@gmail.com>");
                msg.setTo(email);
                msg.setSubject(subject);
                msg.setText(text);
                javaMailSender.send(msg);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
}
