package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.domain.OrderEntity;
import com.example.onlinebookstore.domain.OrderItem;
import com.example.onlinebookstore.domain.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Factory pattern: centralises construction of {@link OrderEntity}/{@link OrderItem}
 * so CartService only orchestrates persistence, not order-building rules.
 */
@Component
public class OrderFactory {
    private final PricingStrategy pricingStrategy;

    public OrderFactory(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public OrderEntity createOrder(User user) {
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        return order;
    }

    public OrderItem createLineItem(Book book, int quantity) {
        return new OrderItem(book.getTitle(), book.getPrice(), quantity);
    }

    public BigDecimal lineTotal(Book book, int quantity) {
        return pricingStrategy.lineTotal(book.getPrice(), quantity);
    }
}
