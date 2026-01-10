package com.example.ecommerce.service.impl;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public User register(User user) throws Exception {
        if (repo.existsByUsername(user.getUsername())) {
            throw new Exception("Username already taken");
        }
        if (repo.existsByEmail(user.getEmail())) {
            throw new Exception("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public User login(String username, String rawPassword) throws Exception {
        Optional<User> maybe = repo.findByUsername(username);
        if (maybe.isEmpty()) throw new Exception("Invalid credentials");
        User u = maybe.get();
        if (!encoder.matches(rawPassword, u.getPassword())) throw new Exception("Invalid credentials");
        return u;
    }
}
