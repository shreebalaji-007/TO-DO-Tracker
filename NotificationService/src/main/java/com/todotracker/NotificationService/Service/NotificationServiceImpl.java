package com.todotracker.NotificationService.Service;


import com.todotracker.NotificationService.Domain.Notification;
import com.todotracker.NotificationService.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Notification createNotification(Notification notification) {
            emailService.sendEmail(notification);
        return notificationRepository.save(notification);
    }
}
