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
            bookRepository.save(Book.builder().title("Effective Java").author("Joshua Bloch").price(new BigDecimal("45.00")).stock(10).build());
            bookRepository.save(Book.builder().title("Clean Code").author("Robert C. Martin").price(new BigDecimal("40.00")).stock(8).build());
            bookRepository.save(Book.builder().title("Java Concurrency in Practice").author("Brian Goetz").price(new BigDecimal("50.00")).stock(5).build());
        };
    }
}
