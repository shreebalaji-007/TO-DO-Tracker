package com.todotracker.todoservice.repository;

import com.todotracker.todoservice.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByUserId(String userId);
}