package com.todotracker.userservice.service;

import com.todotracker.userservice.proxy.NotificationProxy;
import com.todotracker.userservice.proxy.UserProxy;
import com.todotracker.userservice.domain.Notification;
import com.todotracker.userservice.domain.User;
import com.todotracker.userservice.exception.UserAlreadyExistsException;
import com.todotracker.userservice.exception.UserNotFoundException;
import com.todotracker.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserProxy userProxy;
    private NotificationProxy notificationProxy;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProxy userProxy, NotificationProxy notificationProxy) {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
        this.notificationProxy = notificationProxy;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getUsername());
        }
        ResponseEntity r = userProxy.saveUser(user);
        int proxyUserId = (int)((LinkedHashMap) r.getBody()).get("userId");

        user.setUserid(String.valueOf(proxyUserId));
        User createdUser = userRepository.save(user);
        Notification newNotify = new Notification(user.getEmail(), "User Created", "Your user ("+ user.getUsername() +") created successfully.");
        ResponseEntity rn = notificationProxy.sendNotification(newNotify);

        return createdUser;
    }

    @Override
    public User updateUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setUserid(id);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}