package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServiceTest {

    private BookRepository repo;
    private BookService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(BookRepository.class);
        service = new BookService(repo);
    }

    @Test
    void listAllReturnsPage() {
        Book b = new Book("Title","Author", BigDecimal.TEN, 5);
        Page<Book> p = new PageImpl<>(Collections.singletonList(b));
        when(repo.findAll(PageRequest.of(0,20))).thenReturn(p);
        Page<Book> result = service.listAll(PageRequest.of(0,20));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
