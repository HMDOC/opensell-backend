package com.opensell.email;

import com.opensell.email.template.SignUpTemplate;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Mail service to send customizable emails
 *
 * @author Quoc Dung
 * @modifiedBy Achraf
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${SMTP_EMAIL}")
    private String senderEmail;
    private final JavaMailSender mailSender;

    public boolean sendHtmlEmail(String[] to, String subject, String body) throws MessagingException {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message);

            helper.setFrom("Opensell<"+senderEmail+">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void sendNonHtmlEmail(String[] to, String subject, String body) throws MessagingException {
        var message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public boolean sendSignupCode(String email, String username, String code) throws MessagingException {
        return this.sendHtmlEmail(
            new String[]{email},
            "Welcome to OpenSell",
            SignUpTemplate.getEmailVerification(username, code)
        );
    }
}
