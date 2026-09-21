package com.example.onlinebookstore.service;

import com.example.onlinebookstore.domain.*;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService implements CartServicePort {
    private static final Logger LOG = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    public CartService(CartRepository cartRepository, BookRepository bookRepository, OrderRepository orderRepository, OrderFactory orderFactory) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
    }

    @Override
    public Cart getCartForUser(User user) {
        LOG.debug("Get or create cart for user: {}", user.getUsername());
        return cartRepository.findByUser(user).orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    @Override
    @Transactional
    public Cart addItem(User user, Long bookId, Integer qty) {
        LOG.debug("Add item: bookId={}, qty={} for user={}", bookId, qty, user.getUsername());
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Cart cart = getCartForUser(user);
        Optional<CartItem> existing = cart.getItems().stream().filter(i -> i.getBook().getId().equals(bookId)).findFirst();
        if (existing.isPresent()) {
            existing.get().increaseQuantity(qty);
        } else {
            cart.getItems().add(new CartItem(book, qty));
        }
        Cart saved = cartRepository.save(cart);
        LOG.debug("Cart saved for user {}: items={} ", user.getUsername(), saved.getItems().size());
        return saved;
    }

    @Override
    @Transactional
    public Cart updateItem(User user, Long cartItemId, Integer qty) {
        LOG.debug("Update cartItem {} to qty {} for user {}", cartItemId, qty, user.getUsername());
        Cart cart = getCartForUser(user);
        CartItem item = cart.getItems().stream().filter(i -> i.getId().equals(cartItemId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        item.setQuantity(qty);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart removeItem(User user, Long cartItemId) {
        LOG.debug("Remove cartItem {} for user {}", cartItemId, user.getUsername());
        Cart cart = getCartForUser(user);
        cart.getItems().removeIf(i -> i.getId().equals(cartItemId));
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public OrderEntity checkout(User user) {
        LOG.info("Checkout initiated for user {}", user.getUsername());
        Cart cart = getCartForUser(user);
        if (cart.getItems().isEmpty()) {
            LOG.warn("Attempt to checkout empty cart for user {}", user.getUsername());
            throw new IllegalStateException("Cannot checkout empty cart");
        }
        OrderEntity order = orderFactory.createOrder(user);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cart.getItems()) {
            Book b = bookRepository.findById(ci.getBook().getId()).orElseThrow(() -> new IllegalArgumentException("Book missing during checkout"));
            // Use domain behaviour which will throw domain exceptions when invalid
            b.decreaseStock(ci.getQuantity());
            order.getItems().add(orderFactory.createLineItem(b, ci.getQuantity()));
            total = total.add(orderFactory.lineTotal(b, ci.getQuantity()));
            bookRepository.save(b);
        }
        order.setTotal(total);
        OrderEntity saved = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);
        LOG.info("Checkout completed for user {}: orderId={}", user.getUsername(), saved.getId());
        return saved;
    }
}

