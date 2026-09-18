package com.example.onlinebookstore.config;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner init(BookRepository bookRepository) {
        return args -> {
            LOG.info("Initializing sample books");
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", new BigDecimal("45.00"), 10));
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", new BigDecimal("40.00"), 8));
            bookRepository.save(new Book("Java Concurrency in Practice", "Brian Goetz", new BigDecimal("50.00"), 5));
        };
    }
}
