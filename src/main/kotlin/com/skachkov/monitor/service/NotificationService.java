package com.skachkov.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(final String message) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("belrafter@yandex.ru");

        msg.setSubject("Notification message about microservices issues");
        msg.setText(message);

        javaMailSender.send(msg);
    }
}
