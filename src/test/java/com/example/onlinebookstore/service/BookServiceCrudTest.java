package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceCrudTest {
    private BookRepository repo;
    private BookService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(BookRepository.class);
        service = new BookService(repo);
    }

    @Test
    void createAndGetAndDelete() {
        Book b = new Book("C","A", BigDecimal.valueOf(5), 10);
        b.setId(100L);
        when(repo.save(any(Book.class))).thenReturn(b);
        Book created = service.create(b);
        assertNotNull(created);
        assertEquals(100L, created.getId());

        when(repo.findById(100L)).thenReturn(Optional.of(b));
        Book fetched = service.getById(100L);
        assertNotNull(fetched);

        doNothing().when(repo).deleteById(100L);
        service.delete(100L);
        verify(repo, times(1)).deleteById(100L);
    }

    @Test
    void updateExisting() {
        Book existing = new Book("Old","Auth", BigDecimal.ONE, 2);
        existing.setId(200L);
        when(repo.findById(200L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);

        Book update = new Book("New","Auth2", BigDecimal.TEN, 5);
        Book res = service.update(200L, update);
        assertEquals("New", res.getTitle());
        assertEquals(5, res.getStock());
    }
}
