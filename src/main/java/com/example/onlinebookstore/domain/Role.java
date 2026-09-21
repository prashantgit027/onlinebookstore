package com.example.onlinebookstore.domain;

/**
 * Application user roles. Avoids scattering "ADMIN"/"USER" magic strings
 * across the codebase.
 */
public enum Role {
    USER,
    ADMIN
}
