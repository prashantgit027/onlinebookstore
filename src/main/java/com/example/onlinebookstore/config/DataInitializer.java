package com.example.onlinebookstore.config;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner init(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", new BigDecimal("45.00"), 10));
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", new BigDecimal("40.00"), 8));
            bookRepository.save(new Book("Java Concurrency in Practice", "Brian Goetz", new BigDecimal("50.00"), 5));
        };
    }
}
