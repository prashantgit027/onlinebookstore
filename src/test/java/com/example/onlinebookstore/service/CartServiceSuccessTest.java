package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.*;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartServiceSuccessTest {
    private CartRepository cartRepo;
    private BookRepository bookRepo;
    private OrderRepository orderRepo;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepo = Mockito.mock(CartRepository.class);
        bookRepo = Mockito.mock(BookRepository.class);
        orderRepo = Mockito.mock(OrderRepository.class);
        cartService = new CartService(cartRepo, bookRepo, orderRepo, new OrderFactory(new StandardPricingStrategy()));
    }

    @Test
    void checkoutSuccessCreatesOrder() {
        User u = new User("bob","pw");
        Book b = new Book("T","A", BigDecimal.TEN, 5);
        b.setId(2L);
        CartItem ci = new CartItem(b, 2);
        Cart c = new Cart(u);
        c.getItems().add(ci);
        when(cartRepo.findByUser(u)).thenReturn(Optional.of(c));
        when(bookRepo.findById(2L)).thenReturn(Optional.of(b));
        when(orderRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(cartRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        OrderEntity order = cartService.checkout(u);
        assertNotNull(order);
        verify(bookRepo, times(1)).save(any());
        verify(orderRepo, times(1)).save(any());
    }
}
