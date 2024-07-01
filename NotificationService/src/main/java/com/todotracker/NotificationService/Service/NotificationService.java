package com.todotracker.NotificationService.Service;


import com.todotracker.NotificationService.Domain.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
}