package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookServicePort {
    Page<Book> listAll(Pageable pageable);
    Book getById(Long id);
    Book create(Book book);
    Book update(Long id, Book book);
    void delete(Long id);
}
