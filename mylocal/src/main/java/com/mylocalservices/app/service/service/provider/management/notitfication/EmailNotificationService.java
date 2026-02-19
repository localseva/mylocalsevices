package com.mylocalservices.app.service.service.provider.management.notitfication;

import com.mylocalservices.app.entity.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void notifyUser(User user, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Job Update");
        mail.setText(message);
        mailSender.send(mail);
    }
}
