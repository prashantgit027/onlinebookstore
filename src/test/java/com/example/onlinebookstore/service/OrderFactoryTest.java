package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.domain.OrderEntity;
import com.example.onlinebookstore.domain.OrderItem;
import com.example.onlinebookstore.domain.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderFactoryTest {

    private final OrderFactory orderFactory = new OrderFactory(new StandardPricingStrategy());

    @Test
    void createOrderAssignsUser() {
        User user = new User("bob", "pw");
        OrderEntity order = orderFactory.createOrder(user);
        assertNotNull(order);
        assertEquals(user, order.getUser());
    }

    @Test
    void createLineItemCopiesBookDetails() {
        Book book = Book.builder().title("T").author("A").price(BigDecimal.TEN).stock(5).build();
        OrderItem item = orderFactory.createLineItem(book, 3);
        assertEquals("T", item.getBookTitle());
        assertEquals(BigDecimal.TEN, item.getPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
    void lineTotalMultipliesPriceByQuantity() {
        Book book = Book.builder().title("T").author("A").price(BigDecimal.valueOf(9.99)).stock(5).build();
        BigDecimal total = orderFactory.lineTotal(book, 2);
        assertEquals(BigDecimal.valueOf(9.99).multiply(BigDecimal.valueOf(2)), total);
    }
}
