package com.todotracker.userservice.proxy;

import com.todotracker.userservice.domain.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notification-service",url="localhost:8095")
public interface NotificationProxy {
    @PostMapping("/api/notifications")
    public ResponseEntity<?> sendNotification(@RequestBody Notification notification);
}