package com.microservices.drivenzy.otpservice.otpservice.service;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MailService.class);

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        logger.info("Sending mail to {}", to);
        mailSender.send(message);
        logger.info("Sending mail to");
    }
}