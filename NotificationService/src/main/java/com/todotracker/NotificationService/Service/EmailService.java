package com.todotracker.NotificationService.Service;

import com.todotracker.NotificationService.Domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(Notification notification) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("niitproject45@outlook.com");
            message.setReplyTo("niitproject45@outlook.com");
            message.setTo(notification.getRecipient());
            message.setSubject("Notification: " + notification.getSubject());
            message.setText("Hello,\n\n" + notification.getMessage());

            emailSender.send(message);
        }catch(Exception ex){

        }
    }
}
