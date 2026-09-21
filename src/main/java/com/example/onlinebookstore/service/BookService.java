package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService implements BookServicePort {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> listAll(Pageable pageable) { LOG.debug("Listing books page {}", pageable); return bookRepository.findAll(pageable); }

    @Override
    public Book getById(Long id) { LOG.debug("Get book by id: {}", id); return bookRepository.findById(id).orElse(null); }

    @Override
    public Book create(Book book) { return bookRepository.save(book); }

    @Override
    public Book update(Long id, Book book) {
        Book existing = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        existing.setStock(book.getStock());
        return bookRepository.save(existing);
    }

    @Override
    public void delete(Long id) { bookRepository.deleteById(id); }
}
