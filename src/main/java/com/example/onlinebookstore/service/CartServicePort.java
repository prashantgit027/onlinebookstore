package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.Cart;
import com.example.onlinebookstore.domain.OrderEntity;
import com.example.onlinebookstore.domain.User;

/**
 * Service-layer abstraction (DIP) for cart/checkout orchestration, allowing
 * controllers to depend on behaviour rather than a concrete implementation.
 */
public interface CartServicePort {
    Cart getCartForUser(User user);
    Cart addItem(User user, Long bookId, Integer qty);
    Cart updateItem(User user, Long cartItemId, Integer qty);
    Cart removeItem(User user, Long cartItemId);
    OrderEntity checkout(User user);
}
