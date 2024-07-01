package com.todotracker.taskservice.controller;

import com.todotracker.taskservice.domain.Task;
import com.todotracker.taskservice.exception.TaskAlreadyExistsException;
import com.todotracker.taskservice.exception.TaskNotFoundException;
import com.todotracker.taskservice.service.TaskService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(HttpServletRequest request) {
        return taskService.getAllTasks();
    }

    @GetMapping("/todo/{todoId}")
    public List<Task> getTasksByTodoId(@PathVariable String todoId, HttpServletRequest request) {
        return taskService.getTasksByTodoId(todoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id, HttpServletRequest request) {
        try {
            Task task = taskService.getTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            Task createdTask = taskService.createTask(task, userIdEmail);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (TaskAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            Task updatedTask = taskService.updateTask(id, task, userIdEmail);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            taskService.deleteTask(id, userIdEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getIdEmailFromClaims(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7); // Remove "Bearer " prefix
        }

        // Validate and parse the JWT token and extract the subject
        if (jwtToken != null) {
            try {
                Claims claims = Jwts.parser().setSigningKey("capstoneproject").parseClaimsJws(jwtToken).getBody();
                return claims.getSubject();
            } catch (JwtException e) {
                // JWT validation failed
                e.printStackTrace();
            }
        }
        return null;
    }
}