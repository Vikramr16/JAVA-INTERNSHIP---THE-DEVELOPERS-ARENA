package com.example.Task_Management;


import com.example.Task_Management.TaskRepository.UserRepository;
import com.example.Task_Management.TaskService.UserService;
import com.example.Task_Management.controller.AuthController;
import com.example.Task_Management.module.User;
import com.example.Task_Management.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private UserRepository userRepository;

    private UserService userService;


    private PasswordEncoder passwordEncoder;


    private JwtUtil jwtUtil;

    private String email = "test@example.com";
    private String password = "password123";

    @BeforeEach
    void setup() {
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encodedPass");
        Mockito.when(jwtUtil.generateToken(email)).thenReturn("mockedToken");
    }

    // ✅ Test for Registration API
    @Test
    void testRegisterUserSuccess() throws Exception {
        Map<String, String> body = Map.of("email", email, "password", password);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    // ✅ Test for Existing Email
    @Test
    void testRegisterUserEmailAlreadyExists() throws Exception {
        Map<String, String> body = Map.of("email", email, "password", password);

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User( email, password)));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists"));
    }

    // ✅ Test for Login API Success
    @Test
    void testLoginUserSuccess() throws Exception {
        Map<String, String> body = Map.of("email", email, "password", password);
        User user = new User(email, "encodedPass");

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(password, "encodedPass")).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockedToken"));
    }

    // ✅ Test for Login Failure (Wrong Password)
    @Test
    void testLoginUserWrongPassword() throws Exception {
        Map<String, String> body = Map.of("email", email, "password", "wrongpass");
        User user = new User( email, "encodedPass");

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongpass", "encodedPass")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not registered "));
    }
}
