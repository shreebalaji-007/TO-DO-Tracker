package com.todotracker.todoservice.service;

import com.todotracker.todoservice.proxy.NotificationProxy;
import com.todotracker.todoservice.domain.Notification;
import com.todotracker.todoservice.domain.Todo;
import com.todotracker.todoservice.exception.TodoAlreadyExistsException;
import com.todotracker.todoservice.exception.TodoNotFoundException;
import com.todotracker.todoservice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private NotificationProxy notificationProxy;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, NotificationProxy notificationProxy) {
        this.todoRepository = todoRepository;
        this.notificationProxy = notificationProxy;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> getTodosByUserId(String userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    public Todo getTodoById(String id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        return optionalTodo.orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public Todo createTodo(Todo todo, String userIdEmail) {
        String[] tokenParts = userIdEmail.split("\\|");
        todo.setUserId(tokenParts[0]);
        Todo createdTodo = todoRepository.save(todo);
        Notification newNotify = new Notification(tokenParts[1], "Todo Created", "Your todo ("+ todo.getTitle() +") created successfully.");
        ResponseEntity rn = notificationProxy.sendNotification(newNotify);
        return createdTodo;
    }

    @Override
    public Todo updateTodo(String id, Todo todo, String userIdEmail) {
        if (todoRepository.existsById(id)) {
            String[] tokenParts = userIdEmail.split("\\|");
            todo.setUserId(tokenParts[0]);
            Todo updatedTodo =  todoRepository.save(todo);
            Notification newNotify = new Notification(tokenParts[1], "Todo Updated", "Your todo ("+ todo.getTitle() +") updated successfully.");
            ResponseEntity rn = notificationProxy.sendNotification(newNotify);
            return updatedTodo;
        } else {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
    }

    @Override
    public void deleteTodo(String id, String userIdEmail) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            String[] tokenParts = userIdEmail.split("\\|");
            todoRepository.deleteById(id);
            Notification newNotify = new Notification(tokenParts[1], "Todo Deleted", "Your todo ("+ todo.getTitle() +") deleted successfully.");
            ResponseEntity rn = notificationProxy.sendNotification(newNotify);
        } else {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
    }
}
