package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password) {
        User u = new User(username, passwordEncoder.encode(password));
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) { return userRepository.findByUsername(username); }
}
