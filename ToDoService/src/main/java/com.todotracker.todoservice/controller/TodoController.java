package com.todotracker.todoservice.controller;

import com.todotracker.todoservice.domain.Todo;
import com.todotracker.todoservice.exception.TodoAlreadyExistsException;
import com.todotracker.todoservice.exception.TodoNotFoundException;
import com.todotracker.todoservice.service.TodoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos(HttpServletRequest request) {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id, HttpServletRequest request) {
        try {
            Todo todo = todoService.getTodoById(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    public List<Todo> getTodosByUserId(HttpServletRequest request) {
        String userIdEmail  = getIdEmailFromClaims(request);
        String[] tokenParts = userIdEmail.split("\\|");
        return todoService.getTodosByUserId(tokenParts[0]);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            Todo createdTodo = todoService.createTodo(todo, userIdEmail);
            return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
        } catch (TodoAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todo, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            Todo updatedTodo = todoService.updateTodo(id, todo, userIdEmail);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id, HttpServletRequest request) {
        try {
            String userIdEmail  = getIdEmailFromClaims(request);
            todoService.deleteTodo(id, userIdEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TodoNotFoundException e) {
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
