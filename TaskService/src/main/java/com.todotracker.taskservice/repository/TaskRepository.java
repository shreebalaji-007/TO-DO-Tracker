package com.todotracker.taskservice.repository;

import com.todotracker.taskservice.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByTodoId(String todoId);
}
