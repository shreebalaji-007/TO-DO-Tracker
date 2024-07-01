package com.todotracker.taskservice.service;

import com.todotracker.taskservice.proxy.NotificationProxy;
import com.todotracker.taskservice.domain.Notification;
import com.todotracker.taskservice.domain.Task;
import com.todotracker.taskservice.exception.TaskAlreadyExistsException;
import com.todotracker.taskservice.exception.TaskNotFoundException;
import com.todotracker.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private NotificationProxy notificationProxy;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, NotificationProxy notificationProxy) {
        this.taskRepository = taskRepository;
        this.notificationProxy = notificationProxy;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksByTodoId(String todoId) {
        return taskRepository.findByTodoId(todoId);
    }

    @Override
    public Task getTaskById(String id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Override
    public Task createTask(Task task, String userIdEmail) {
        String[] tokenParts = userIdEmail.split("\\|");
        Task createdTask = taskRepository.save(task);
        Notification newNotify = new Notification(tokenParts[1], "Task Created", "Your task ("+ task.getName() +") with due date as "+ task.getDueDate() +" created successfully.");
        ResponseEntity rn = notificationProxy.sendNotification(newNotify);
        return createdTask;
    }

    @Override
    public Task updateTask(String id, Task task, String userIdEmail) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            String[] tokenParts = userIdEmail.split("\\|");
            Task updatedTask = taskRepository.save(task);
            Notification newNotify = new Notification(tokenParts[1], "Task Updated", "Your task ("+ task.getName() +") with due date as "+ task.getDueDate() +" updated successfully.");
            ResponseEntity rn = notificationProxy.sendNotification(newNotify);
            return updatedTask;
        } else {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
    }

    @Override
    public void deleteTask(String id, String userIdEmail) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            String[] tokenParts = userIdEmail.split("\\|");
            taskRepository.deleteById(id);
            Notification newNotify = new Notification(tokenParts[1], "Task Deleted", "Your task ("+ task.getName() +") with due date as "+ task.getDueDate() +" deleted successfully.");
            ResponseEntity rn = notificationProxy.sendNotification(newNotify);
        } else {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
    }
}
