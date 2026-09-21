package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.dto.BookDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import com.example.onlinebookstore.service.BookServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private final BookServicePort bookService;

    public BookController(BookServicePort bookService) { this.bookService = bookService; }

    @GetMapping
    public ResponseEntity<Page<Book>> list(Integer page, Integer size, String sort) {
        LOG.debug("Listing books page={}, size={}, sort={}", page, size, sort);
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0) ? 20 : size;
        Sort sortObj = parseSort(sort);
        Pageable pageable = PageRequest.of(p, s, sortObj);
        Page<Book> result = bookService.listAll(pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * Parses a "property" or "property,asc|desc" sort parameter, matching the
     * convention Spring Data's Pageable resolver uses (e.g. "title,asc").
     * Falls back to sorting by "id" when no sort parameter is supplied.
     */
    private Sort parseSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("id");
        }
        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            direction = Sort.Direction.DESC;
        }
        return Sort.by(direction, property);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        LOG.debug("Fetching book by id: {}", id);
        Book b = bookService.getById(id);
        if (b == null) {
            LOG.warn("Book not found: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(b);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookDto dto) {
        Book b = new Book(dto.getTitle(), dto.getAuthor(), dto.getPrice(), dto.getStock());
        Book created = bookService.create(b);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody BookDto dto) {
        Book b = new Book(dto.getTitle(), dto.getAuthor(), dto.getPrice(), dto.getStock());
        Book updated = bookService.update(id, b);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
