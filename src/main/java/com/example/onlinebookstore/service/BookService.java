package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listAll() { return bookRepository.findAll(); }
    public Book getById(Long id) { return bookRepository.findById(id).orElse(null); }
}
