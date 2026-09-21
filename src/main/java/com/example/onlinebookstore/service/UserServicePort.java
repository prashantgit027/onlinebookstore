package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.User;

import java.util.Optional;

/**
 * Service-layer abstraction (DIP) for user registration/lookup.
 */
public interface UserServicePort {
    User register(String username, String password);
    Optional<User> findByUsername(String username);
}
