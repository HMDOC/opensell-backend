package com.opensell.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Quoc Dung
 */
@CrossOrigin("http://localhost/")
@RestController
@Service
public class EmailController {    
        @Autowired
        private JavaMailSender javaMailSender;
    
        @GetMapping("/verification")
        public void sendEmail(@RequestParam String email, @RequestParam String subject, @RequestParam String text) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject(subject);
            msg.setText(text);
            javaMailSender.send(msg);
        }
}
