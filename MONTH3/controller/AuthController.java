package com.example.Task_Management.controller;

import com.example.Task_Management.TaskRepository.UserRepository;
import com.example.Task_Management.TaskService.UserService;
import com.example.Task_Management.module.User;
import com.example.Task_Management.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");

        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email alreaady exists",HttpStatus.CONFLICT);
        }

       userService.createUser(User.builder().email(email).password(password).build());

        /*User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userService.createUser(user);*/
        return new ResponseEntity<>("Successfully Registered",HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("User not registered", HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        if(passwordEncoder.matches(password,user.getPassword())){
            return new ResponseEntity<>("User not registered ", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));
    }
}
