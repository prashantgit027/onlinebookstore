package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.domain.Cart;
import com.example.onlinebookstore.domain.OrderEntity;
import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.dto.AddToCartRequest;
import com.example.onlinebookstore.dto.RemoveCartItemRequest;
import com.example.onlinebookstore.dto.UpdateCartItemRequest;
import com.example.onlinebookstore.service.CartServicePort;
import com.example.onlinebookstore.service.UserServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

    private final CartServicePort cartService;
    private final UserServicePort userService;

    public CartController(CartServicePort cartService, UserServicePort userService) { this.cartService = cartService; this.userService = userService; }

    private User currentUser(Principal p) {
        return userService.findByUsername(p.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @GetMapping
    public ResponseEntity<Cart> get(Principal p) {
        LOG.debug("Get cart for user: {}", p.getName());
        return ResponseEntity.ok(cartService.getCartForUser(currentUser(p)));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> add(Principal p, @Valid @RequestBody AddToCartRequest req) {
        LOG.debug("Adding book {} (qty {}) to cart for user {}", req.getBookId(), req.getQty(), p.getName());
        return ResponseEntity.ok(cartService.addItem(currentUser(p), req.getBookId(), req.getQty()));
    }

    @PostMapping("/update")
    public ResponseEntity<Cart> update(Principal p, @Valid @RequestBody UpdateCartItemRequest req) {
        LOG.debug("Updating cart item {} to qty {} for user {}", req.getCartItemId(), req.getQty(), p.getName());
        return ResponseEntity.ok(cartService.updateItem(currentUser(p), req.getCartItemId(), req.getQty()));
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> remove(Principal p, @Valid @RequestBody RemoveCartItemRequest req) {
        LOG.debug("Removing cart item {} for user {}", req.getCartItemId(), p.getName());
        return ResponseEntity.ok(cartService.removeItem(currentUser(p), req.getCartItemId()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderEntity> checkout(Principal p) {
        LOG.info("Checkout requested by user {}", p.getName());
        return ResponseEntity.ok(cartService.checkout(currentUser(p)));
    }
}

