package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.dto.LoginRequest;
import com.example.onlinebookstore.dto.RegisterRequest;
import com.example.onlinebookstore.security.JwtUtil;
import com.example.onlinebookstore.service.UserServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final UserServicePort userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserServicePort userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) { this.userService = userService; this.authenticationManager = authenticationManager; this.jwtUtil = jwtUtil; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (userService.findByUsername(req.getUsername()).isPresent()) {
            LOG.warn("Registration attempt for existing username: {}", req.getUsername());
            return ResponseEntity.status(400).body("username exists");
        }
        User u = userService.register(req.getUsername(), req.getPassword());
        LOG.info("Registered new user: {}", req.getUsername());
        return ResponseEntity.status(201).body(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            String token = jwtUtil.generateToken(req.getUsername());
            Map<String,String> resp = new HashMap<>();
            resp.put("token", token);
            LOG.info("User logged in: {}", req.getUsername());
            return ResponseEntity.ok(resp);
        } catch (AuthenticationException ex) {
            LOG.warn("Failed login for user: {}", req.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

