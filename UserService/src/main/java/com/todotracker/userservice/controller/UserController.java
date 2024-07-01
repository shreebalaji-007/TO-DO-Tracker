package com.todotracker.userservice.controller;

import com.todotracker.userservice.domain.User;
import com.todotracker.userservice.exception.UserAlreadyExistsException;
import com.todotracker.userservice.exception.UserNotFoundException;
import com.todotracker.userservice.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/detail")
    public ResponseEntity<User> getUserById(HttpServletRequest request) {
        try {
            String userId  = getIdFromClaims(request);
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            String userId  = getIdFromClaims(request);
            User updatedUser = userService.updateUser(userId, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(HttpServletRequest request) {
        try {
            String userId  = getIdFromClaims(request);
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getIdFromClaims(HttpServletRequest request){
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
