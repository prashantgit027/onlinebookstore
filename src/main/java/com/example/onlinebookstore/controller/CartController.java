package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.domain.Cart;
import com.example.onlinebookstore.domain.OrderEntity;
import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.service.CartService;
import com.example.onlinebookstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) { this.cartService = cartService; this.userService = userService; }

    private User currentUser(Principal p) {
        return userService.findByUsername(p.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @GetMapping
    public ResponseEntity<Cart> get(Principal p) { return ResponseEntity.ok(cartService.getCartForUser(currentUser(p))); }

    @PostMapping("/add")
    public ResponseEntity<Cart> add(Principal p, @RequestBody Map<String, String> body) {
        Long bookId = Long.valueOf(body.get("bookId"));
        Integer qty = Integer.valueOf(body.getOrDefault("qty","1"));
        return ResponseEntity.ok(cartService.addItem(currentUser(p), bookId, qty));
    }

    @PostMapping("/update")
    public ResponseEntity<Cart> update(Principal p, @RequestBody Map<String, String> body) {
        Long cartItemId = Long.valueOf(body.get("cartItemId"));
        Integer qty = Integer.valueOf(body.get("qty"));
        return ResponseEntity.ok(cartService.updateItem(currentUser(p), cartItemId, qty));
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> remove(Principal p, @RequestBody Map<String, String> body) {
        Long cartItemId = Long.valueOf(body.get("cartItemId"));
        return ResponseEntity.ok(cartService.removeItem(currentUser(p), cartItemId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderEntity> checkout(Principal p) { return ResponseEntity.ok(cartService.checkout(currentUser(p))); }
}
