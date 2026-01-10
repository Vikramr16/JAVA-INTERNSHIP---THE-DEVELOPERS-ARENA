package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;

public interface UserService {
    User register(User user) throws Exception;
    User login(String username, String rawPassword) throws Exception;
}
