package com.todotracker.NotificationService.Repository;


import com.todotracker.NotificationService.Domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    // You can define custom query methods here if needed
}