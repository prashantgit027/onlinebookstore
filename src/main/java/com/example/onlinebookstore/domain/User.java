package com.example.onlinebookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password; // stored hashed; never serialized in API responses

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
