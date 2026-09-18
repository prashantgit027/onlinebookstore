package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listAll() { LOG.debug("Listing all books"); return bookRepository.findAll(); }
    public Book getById(Long id) { LOG.debug("Get book by id: {}", id); return bookRepository.findById(id).orElse(null); }
}
