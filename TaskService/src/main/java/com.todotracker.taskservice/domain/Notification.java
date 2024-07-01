package com.todotracker.taskservice.domain;
import java.util.UUID;

public class Notification {
    private String id;
    private String recipient;
    private String subject;
    private String message;

    public Notification() {
    }

    public Notification(String recipient, String subject, String message) {
        this.id = UUID.randomUUID().toString();
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}