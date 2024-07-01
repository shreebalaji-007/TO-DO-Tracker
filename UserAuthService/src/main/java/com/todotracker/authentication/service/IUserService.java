package com.todotracker.authentication.service;

import com.todotracker.authentication.domain.User;
import com.todotracker.authentication.exception.UserAlreadyExistsException;
import com.todotracker.authentication.exception.InvalidCredentialsException;

public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByUsernameAndPassword(String username, String password) throws InvalidCredentialsException;
}

