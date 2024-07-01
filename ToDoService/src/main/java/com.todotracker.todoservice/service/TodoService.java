package com.todotracker.todoservice.service;

import com.todotracker.todoservice.domain.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();
    List<Todo> getTodosByUserId(String userId);
    Todo getTodoById(String id);
    Todo createTodo(Todo todo, String userIdEmail);
    Todo updateTodo(String id, Todo todo, String userIdEmail);
    void deleteTodo(String id, String userIdEmail);
}
