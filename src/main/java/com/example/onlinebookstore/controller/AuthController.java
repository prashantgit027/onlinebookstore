package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.security.JwtUtil;
import com.example.onlinebookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) { this.userService = userService; this.authenticationManager = authenticationManager; this.jwtUtil = jwtUtil; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username==null||password==null) return ResponseEntity.badRequest().body("username and password required");
        if (userService.findByUsername(username).isPresent()) {
            LOG.warn("Registration attempt for existing username: {}", username);
            return ResponseEntity.status(400).body("username exists");
        }
        User u = userService.register(username, password);
        LOG.info("Registered new user: {}", username);
        return ResponseEntity.status(201).body(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username==null||password==null) return ResponseEntity.badRequest().body("username and password required");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtUtil.generateToken(username);
            Map<String,String> resp = new HashMap<>();
            resp.put("token", token);
            LOG.info("User logged in: {}", username);
            return ResponseEntity.ok(resp);
        } catch (AuthenticationException ex) {
            LOG.warn("Failed login for user: {}", username);
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
