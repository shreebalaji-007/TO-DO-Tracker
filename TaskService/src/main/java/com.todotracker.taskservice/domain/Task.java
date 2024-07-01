package com.todotracker.taskservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private Date dueDate;
    private String description;
    private String priority;
    private String status;
    private String todoId;

    public Task() {
    }

    public Task(String name, Date dueDate, String description, String priority, String status,String todoId) {
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.todoId = todoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }
}