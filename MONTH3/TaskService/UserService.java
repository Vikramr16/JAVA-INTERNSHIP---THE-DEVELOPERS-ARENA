package com.example.Task_Management.TaskService;

import com.example.Task_Management.TaskRepository.UserRepository;
import com.example.Task_Management.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user){ userRepository.save(user); }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
    }

}
