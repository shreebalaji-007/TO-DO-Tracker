package com.todotracker.authentication.security;



import com.todotracker.authentication.domain.User;

import java.util.Map;


public interface SecurityTokenGenerator {
    String createToken(User user);

    Map<String, String> generateToken(User user);//token and message -> the return type can be String also
}
