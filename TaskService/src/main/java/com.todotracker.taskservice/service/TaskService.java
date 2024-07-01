package com.todotracker.taskservice.service;

import com.todotracker.taskservice.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    List<Task> getTasksByTodoId(String todoId);
    Task getTaskById(String id);
    Task createTask(Task task, String userIdEmail);
    Task updateTask(String id, Task task, String userIdEmail);
    void deleteTask(String id, String userIdEmail);
}

